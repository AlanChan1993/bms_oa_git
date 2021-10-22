package com.infinitus.bms_oa.task;

import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.enums.BmsOaLogStatusEnum;
import com.infinitus.bms_oa.enums.OaFlagEnum;
import com.infinitus.bms_oa.pojo.*;
import com.infinitus.bms_oa.service.BmsBillAdujestService;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import com.infinitus.bms_oa.utils.Httputil;
import com.infinitus.bms_oa.utils.InfinitusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScheduledTasks {
    @Autowired
    private BmsBillAdujestService billService;

    @Autowired
    private Bms_OA_logService logService;

    @Value("${BMS.URL.billToOA}")
    private String  url;

    private  static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private  static final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 每30s处理一次   1000 * 1 * 30
     */
    @Scheduled(fixedRate = 1000 * 2 * 30)
    public void bmsToOA() {
        String keyValue = simpleDateFormat.format(new Date());
        try {
            log.info("【定时任务bmsToOA()处理开始】：keyValue:{}", keyValue);
            String statusFlag = BmsSynOA();
            log.info("【定时任务bmsToOA()处理结束】:statusFlag:{}", statusFlag);
        } catch (Exception ex) {
            log.info("【ScheduledTasks】执行错误:ex:{}", ex);
        }
    }

    /**
     * 每15s处理一次   1000 * 1 * 15
     */
    @Scheduled(fixedRate = 1000 * 3 * 15)
    public void updateBillStatus() {
        String keyValue = simpleDateFormat.format(new Date());
        try {
            log.info("【定时任务updateBillStatus()处理开始】：keyValue:{}", keyValue);
            scheduledBmsOaLog();
            log.info("【定时任务updateBillStatus()处理结束】");
        } catch (Exception ex) {
            log.info("【ScheduledTasks】updateBillStatus执行错误:ex:{}", ex);
        }
    }

    /**
     * BMS_OA ->多对一
     * 1.取各类型数据，运输/仓储:异常，调整，扣款
     * 2.封装数据{
     *     Infinitus.MainTable.SelectUserV20
     *     Infinitus.InfinitusDetailTables.InfinitusDetailTablesRow
     * }
     * 3.post数据到OA
     * 4.改变bms_oa_log的同步状态与同步时间
     * 5.改变两张流程表的oa_flag
     * */
    public String BmsSynOA(){
        //1.从bms_oa_log取合并的未上传的单号
        List<Bms_OA_log> logList = logService.getBmsOaLog();
        List<String> noArray = new ArrayList<>();
        logList.stream().forEach(e->{
            Infinitus infinitus = new Infinitus();//Infinitus|-|Json第一层
            String loginName = "";
            if (null != e.getCreate_id()&&!"".equals(e.getCreate_id())) {
                loginName = billService.selectLoginNameById(e.getCreate_id());
            }
            infinitus.setWorkcode(loginName);//合并人 loginName
            infinitus.setWorkflowId("367");
            infinitus.setRequestName(e.getCode());//主单号

            InfinitusMainTable table = new InfinitusMainTable(); //MainTable 主表
            List<InfinitusDetailTables> detailTablesList = new ArrayList<>();//明细表，第一层
            InfinitusDetailTables infinitusDetailTables = new InfinitusDetailTables();
            infinitusDetailTables.setIndex(1);
            List<InfinitusDetailTablesRow> infinitusDetailTablesRowList = new ArrayList<>();//明细表第二层，具体明细

            InfinitusSelectUserV20 infinitusSelectUserV20 = new InfinitusSelectUserV20();//MainTable.SelectUserV20第三层
            infinitusSelectUserV20.setType("resourceId");
            infinitusSelectUserV20.setValue(loginName);
            table.setSqr(infinitusSelectUserV20);

            Double d = new Double(0);//用于计算总金额

            //2.用单号查找对应流程表详细数据
            String[] nos = e.getBill_code().split(",");
            if(null==nos||"".equals(nos))return;
            for (String no : nos) {
                noArray.add(no);
            }
            List<BmsBillAdjust> adjustList = billService.getBillListDetail(noArray);
            //3.打包数据提交接口:
            for (int i = 0; i < adjustList.size(); i++) {
                if (null == adjustList.get(i).getSettle_year_month() || "".equals(adjustList.get(i).getSettle_year_month())) {
                    adjustList.get(i).setSettle_year_month(new Date());
                }
                table.setJsny(simpleDateFormat2.format(adjustList.get(i).getSettle_year_month()));//需求指出每个结算年月一致，就随机取最后一个为准
                //封装每行明细的数据
                InfinitusDetailTablesRow infinitusDetailTablesRow
                        = new InfinitusUtil().setInfinitusDetailTablesRow(adjustList.get(i));

                //回收infinitusDetailTablesRow==>>infinitusDetailTablesRowList
                infinitusDetailTablesRowList.add(infinitusDetailTablesRow);
                d += adjustList.get(i).getAdj_amount();
            }
            table.setZje(d);
            table.setDh(e.getCode());
            infinitus.setMainTable(table);
            infinitusDetailTables.setRows(infinitusDetailTablesRowList);
            detailTablesList.add(infinitusDetailTables);
            infinitus.setDetailTables(detailTablesList);

            String jsonObject = JSONObject.toJSONString(infinitus);
            log.info("【BmsSynOA】····，jsonObject:{}",jsonObject);
            //4.httppost提交数据到OA
            JSONObject resultJson = Httputil.doPostJson(url,jsonObject,"");
            log.info("【提交接口返回数据resultJson】----:resultJson:{}", resultJson);
            if (null != resultJson.get("success") && resultJson.get("success").equals(true)) {
                log.info("【BmsSynOA修改已传oa_flag的值】···noArray:{}", noArray);
                billService.updateOA_flag(OaFlagEnum.SUCCESS.getCode(), noArray);//提交成功则改变oa_flag的值0 标识未上传  2 已经上传  4上传失败
                logService.updateOaFlag(OaFlagEnum.SUCCESS.getCode(), e.getCode());
                log.info("【BmsSynOA修改已传oa_flag的值】", OaFlagEnum.SUCCESS.getMsg());
            } else {
                log.info("【BmsSynOA修改传输失败的oa_flag的值】···noArray:{}", noArray);
                billService.updateOA_flag(OaFlagEnum.FALSE.getCode(), noArray);
                logService.updateOaFlag(OaFlagEnum.FALSE.getCode(), e.getCode());
                log.info("【BmsSynOA修改传输失败的oa_flag的值】", OaFlagEnum.FALSE.getMsg());
            }
        });
        return "ok";
    }

    /**
     * 1.定时巡查bms_oa_log表，status=2，则为已经同步并审批的记录
     * 2.将此条记录中的两张流程表中的记录的审批状态status=20，审批时间修改
     * 3.bms_oa_log 审批状态，2：已审批，需改流程表状态；4：已改
     */
    public void scheduledBmsOaLog() {
        try {
            //查询需要更新流程表的数据
            List<Bms_OA_log> logList = logService.getBillCodeByStatus(BmsOaLogStatusEnum.APPROVAL.getCodeString());
            if (logList.size() < 1) return;
            logList.stream().forEach(e->{
                String billCode = e.getBill_code();
                List<String> stringList = Arrays.asList(billCode.split(","));
                //更新流程表status与审批时间
                billService.updateStatusAndApeDate(stringList, e.getApproval_dt(), "20");
            });
            List<String> list = new ArrayList<>();
            logList.stream().forEach(e->{
                list.add(e.getCode());
            });
            if (list.size() < 1) return;
            //改变bms_oa_log的status状态
            logService.updateLogStatus(BmsOaLogStatusEnum.APPROVALED.getCodeString(), list);
            log.info("【scheduledBmsOaLog】，执行success,logList:{}", logList);
        } catch (Exception e) {
            log.info("【scheduledBmsOaLog】，执行错误，e:{}", e);
        }

    }



}
