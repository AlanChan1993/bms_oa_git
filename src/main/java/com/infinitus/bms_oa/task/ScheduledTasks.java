package com.infinitus.bms_oa.task;

import com.alibaba.fastjson.JSONObject;
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

    private  static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 每30s处理一次   1000 * 1 * 30
     */
    @Scheduled(fixedRate = 1000 * 2 * 30)
    public void bmsToOA() {
        String keyValue = simpleDateFormat.format(new Date());
        try {
            log.info("【定时任务 处理开始】：keyValue:{}", keyValue);
            String statusFlag = BmsSynOA();
            log.info("【定时任务 处理结束】:statusFlag:{}", statusFlag);
        } catch (Exception ex) {
            log.info("【ScheduledTasks】执行错误:ex:{}", ex);
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
                table.setJsny(simpleDateFormat.format(adjustList.get(i).getSettle_year_month()));//需求指出每个结算年月一致，就随机取最后一个为准
                //封装每行明细的数据
                InfinitusDetailTablesRow infinitusDetailTablesRow
                        = new InfinitusUtil().setInfinitusDetailTablesRow(adjustList.get(i));

                //回收infinitusDetailTablesRow==>>infinitusDetailTablesRowList
                infinitusDetailTablesRowList.add(infinitusDetailTablesRow);
                d += adjustList.get(i).getAdj_amount();//todo 总金额计算
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
     * 定时巡查bms_oa_log表，status=2，则为已经同步并审批的记录
     * 将此条记录中的两张流程表中的记录的审批状态status=20，审批时间修改
     */
    public void scheduledBmsOaLog() {//todo

    }



}
