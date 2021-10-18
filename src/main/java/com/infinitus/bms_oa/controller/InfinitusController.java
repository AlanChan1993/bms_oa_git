package com.infinitus.bms_oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.enums.CodeEnum;
import com.infinitus.bms_oa.enums.OaFlagEnum;
import com.infinitus.bms_oa.pojo.*;
import com.infinitus.bms_oa.pojo.DTO.BillStatusDTO;
import com.infinitus.bms_oa.pojo.VO.ResultVO;
import com.infinitus.bms_oa.service.BmsBillAdujestService;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import com.infinitus.bms_oa.utils.DateUtil;
import com.infinitus.bms_oa.utils.Httputil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Infinitus")
public class InfinitusController {

    @Autowired
    private BmsBillAdujestService service;

    @Autowired
    private Bms_OA_logService logService;

    @Value("${BMS.URL.billToOA}")
    private String url;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @RequestMapping("/Bill")
    public BmsBillAdjust bmsBill(Integer id) {
        return service.selectAdujestById(id);
    }

    @RequestMapping("selectLoginNameById")
    public String selectLoginNameById(String id) {
        return service.selectLoginNameById(id);
    }

    @RequestMapping("selectIdByBillFlag")
    public List<String> selectIdByBillFlag() {
        return service.selectIdByBillFlag();
    }

    @RequestMapping("selectBillByBillFlag")
    public List<BmsBillAdjust> selectBillByBillFlag() {
        return service.selectBillByBillFlag();
    }

    /**
     * 1.设置url，后面改成配置在yml中
     * 2.提取数据
     * 3.打包数据提交接口
     * 4.接收返回实例
     */
    @PostMapping("/hello")
    public String BmsToOA() {
        //1.设置url，后面改成配置在yml中
        //String url = "https://oa-test.infinitus-int.com/api/infinitusint/public/workflow/doCreateWorkflowRequest";
        //2.提取数据
        try {
            List<BmsBillAdjust> list = service.selectBillByBillFlag();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String login_name = service.selectLoginNameById(list.get(i).getCreate_id());
                    //3.打包数据提交接口:  Infinitus   Json第一层
                    Infinitus infinitus = new Infinitus();
                    infinitus.setWorkcode(login_name);
                    infinitus.setWorkflowId("367");
                    //MainTable   第二层
                    InfinitusMainTable table = new InfinitusMainTable();
                    table.setJsny(simpleDateFormat.format(list.get(i).getSettle_year_month()));
                    table.setDh(list.get(i).getAdj_no());

                    //SelectUserV20第三层
                    InfinitusSelectUserV20 infinitusSelectUserV20 = new InfinitusSelectUserV20();
                    infinitusSelectUserV20.setType("resourceId");
                    infinitusSelectUserV20.setValue(login_name);
                    table.setSqr(infinitusSelectUserV20);
                    infinitus.setMainTable(table);
                    //明细表第一层
                    InfinitusDetailTables infinitusDetailTables = new InfinitusDetailTables();
                    infinitusDetailTables.setIndex(1);
                    //明细表第二层,具体明细
                    InfinitusDetailTablesRow infinitusDetailTablesRow = new InfinitusDetailTablesRow();
                    infinitusDetailTablesRow.setDh(list.get(i).getAdj_no());
                    infinitusDetailTablesRow.setSqtjrq(simpleDateFormat.format(list.get(i).getAdj_dt()));//申请提交日期
                    infinitusDetailTablesRow.setJsny(simpleDateFormat.format(list.get(i).getSettle_year_month()));
                    infinitusDetailTablesRow.setSqr(login_name);
                    //todo 判断 费用调整还是运输
                    if (list.get(i).getAdj_no().indexOf("IA-") >= 0) {
                        if (null != list.get(i).getOrg_fee()) {
                            table.setZje(list.get(i).getOrg_fee() + list.get(i).getAdj_amount());
                        } else {
                            table.setZje(list.get(i).getAdj_amount());
                        }

                        //判断调整类型(运输或是仓储)
                        if ("01".equals(list.get(i).getAdj_type())) {
                            table.setYsycdzje(list.get(i).getAdj_amount());
                            infinitusDetailTablesRow.setDznr("运费费用调整");
                            //table.setYskkje(list.get(i).getAdj_amount());ko
                        } else {
                            table.setCcycdzje(list.get(i).getAdj_amount());
                            infinitusDetailTablesRow.setDznr("仓储费用调整");
                            //table.setCckkdzje(list.get(i).getAdj_amount());
                        }
                    } else if (list.get(i).getAdj_no().indexOf("TZ-") >= 0) {
                        table.setZje(list.get(i).getAdj_amount());//todo 总金额
                        //判断调整类型(运输或是仓储)
                        if ("01".equals(list.get(i).getAdj_type())) {
                            table.setYsfydzje(list.get(i).getAdj_amount());
                            infinitusDetailTablesRow.setDznr("运费费用调整");
                        } else {
                            table.setCcfydzje(list.get(i).getAdj_amount());
                            infinitusDetailTablesRow.setDznr("仓储费用调整");
                        }
                    }

                    infinitusDetailTablesRow.setJsc(service.getJscName(list.get(i).getSettle_wh_code()));
                    infinitusDetailTablesRow.setGys(service.getGysName(list.get(i).getVendor_no()));
                    infinitusDetailTablesRow.setCwkm(service.getCwkmName(list.get(i).getFinance_account_no()));

                    String key = list.get(i).getOwner_key();
                    if (key != null && !"".equals(key)) {
                        infinitusDetailTablesRow.setHzdm(key);
                        infinitusDetailTablesRow.setHzmc(service.getOwnerNameByKey(key));//货主名称
                    }
                    //从数据字典中取调整原因描述
                    PlatCode platCode = null;
                    if (null != service.getPlatCode(list.get(i).getAdj_reason())) {
                        platCode = service.getPlatCode(list.get(i).getAdj_reason());
                    }
                    if (platCode.getName() != null && !"".equals(platCode.getName())) {
                        infinitusDetailTablesRow.setDzyy(platCode.getName());

                    }
                    infinitusDetailTablesRow.setDzyysm(list.get(i).getComments());
                    infinitusDetailTablesRow.setDzje(list.get(i).getAdj_amount());
                    infinitusDetailTablesRow.setLy("");
                    infinitusDetailTablesRow.setFjmxpz(list.get(i).getAttachment());
                    List<InfinitusDetailTablesRow> tablesRowList = new ArrayList<>();
                    tablesRowList.add(infinitusDetailTablesRow);
                    infinitusDetailTables.setRows(tablesRowList);
                    List<InfinitusDetailTables> detailTablesList = new ArrayList<>();
                    detailTablesList.add(infinitusDetailTables);
                    infinitus.setDetailTables(detailTablesList);
                    infinitus.setRequestName(list.get(i).getAdj_no());

                    //4.接收返回实例
                    String jsonObject = JSONObject.toJSONString(infinitus);
                    log.info("【提交接口Json数据】----:jsonObject:{}", jsonObject);
                    JSONObject resultJson = Httputil.doPostJson(url, jsonObject, "");
                    log.info("【提交接口返回数据resultJson】----:resultJson:{}", resultJson);
                    if (resultJson.get("success") != null && resultJson.get("success").equals(true)) {
                        service.updateOA_flag("2", list.get(i).getId(), list.get(i).getAdj_no());
                        //提交成功则改变oa_flag的值0 标识未上传  2 已经上传  4上传失败
                        log.info("【修改已传oa_flag的值】···list.get(i).getId():{}", list.get(i).getId());
                        log.info("【修改已传oa_flag的值】···list.get(i).getAdj_no():{}", list.get(i).getAdj_no());
                    } else {
                        service.updateOA_flag("4", list.get(i).getId(), list.get(i).getAdj_no());
                        log.info("【修改传输失败的oa_flag的值】···list.get(i).getId():{}", list.get(i).getId());
                        log.info("【修改传输失败的oa_flag的值】···list.get(i).getAdj_no():{}", list.get(i).getAdj_no());
                    }
                }
            }
            return "ok";
        } catch (Exception ex) {
            log.info("···【提交失败InfinitusController.BmsToOA()】,ex：{}", ex);
            return "fail";
        }
    }

    /**
     * 修改流程审核单号的状态跟时间
     */
    @RequestMapping("updateBillStatus")
    public ResultVO updateBillStatus(@RequestBody BillStatusDTO statusDTO) throws ParseException {
        ResultVO resultVO = new ResultVO();

        Date date = null;
        if (null != statusDTO.getApproval_dt() && !"".equals(statusDTO.getApproval_dt())) {
            date = simpleDateFormat.parse(statusDTO.getApproval_dt());
        } else {
            date = new Date();
        }
        boolean a = false;
        if (null != statusDTO.getAdj_no() && !"".equals(statusDTO.getAdj_no())) {
            a = service.updateStatusAndDate(statusDTO.getAdj_no(), date);
            log.info("【updateBillStatus】成功,statusDTO.getAdj_no():{}", statusDTO.getAdj_no());
        }
        resultVO.setSuccess(String.valueOf(a));
        return resultVO;
    }

    /**
     * 修改主单号status
     */
    @RequestMapping("modifyLogStatus")
    public ResultVO modifyLogStatus(@RequestBody BillStatusDTO statusDTO) throws ParseException {
        ResultVO resultVO = new ResultVO();
        Date date = null;
        if (null != statusDTO.getApproval_dt() && !"".equals(statusDTO.getApproval_dt())) {
            date = simpleDateFormat.parse(statusDTO.getApproval_dt());
        } else {
            date = new Date();
        }
        boolean a = false;
        if (null != statusDTO.getCode() && !"".equals(statusDTO.getCode())) {
            a = logService.modifyLogStatus(statusDTO.getCode(), date);
            log.info("【updateBillStatus】成功,statusDTO.getCode():{}", statusDTO.getCode());
        }
        resultVO.setSuccess(String.valueOf(a));
        return resultVO;
    }

    /**
     * 同步按钮接口
     * 1.拿到登录人Id，通过id去查找此id创建的，未同步的两张表的流程单
     * 2.查询出的adj_no逗号隔开
     * 3.插入bms_oa_log(生成主单号)
     * 4.update流程表中的log_code字段
     */
    @RequestMapping("getBillByCreator")
    public void getBillByCreator(String create_id) {
        try {
            //1、取adj_no
            List<String> adjList = logService.getBmsOaLogByCreateId(create_id);
            //2.处理adj_no
            StringBuffer nos = new StringBuffer();
            adjList.stream().forEach(e -> {
                nos.append(e).append(",");
            });
            //3.生成主单号,创建bms_oa_log
            String code = "BMS-" + new DateUtil().getNowDate();
            logService.createBmsOaLog(code, nos.toString(), create_id);
            //4.update 主单号到流程表的 log_code字段
            logService.updateBillLogCode(code, adjList);
        } catch (Exception e) {
            log.info("【getBillByCreator】，e:{}", e);
        }
    }

    /**
     * 重置按钮接口
     * 1.去除bill_code将其oa_flag改为0（表示需要重新同步）
     * 2.将OA_flag改为8（oa已删除，bms已重置）
     */
    @RequestMapping("updateOaFlag")
    public void updateOaFlag(BillStatusDTO dto) {
        Bms_OA_log bms_oa_log = new Bms_OA_log();
        String bill_code="";
        List<String> strings = new ArrayList<>();
        try {
            if (null != dto.getCode()) {
                bms_oa_log = logService.getBmsOaLogByCode(dto.getCode());
                bill_code = bms_oa_log.getBill_code();
                strings = Arrays.asList(bill_code.split(","));
                service.updateOA_flag(OaFlagEnum.SUCCESS.getCode(), strings);
                logService.updateOaFlag(OaFlagEnum.OA_DELETE.getCode(), dto.getCode());
            }
            log.info("【updateOaFlag】，修改oa_flag执行SUCCESS");
        } catch (Exception ex) {
            log.info("【updateOaFlag】，修改oa_flag执行错误，ex:{}", ex);
        }
    }
}
