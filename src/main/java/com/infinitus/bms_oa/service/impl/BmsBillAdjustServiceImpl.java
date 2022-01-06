package com.infinitus.bms_oa.service.impl;

import com.infinitus.bms_oa.mapper.BmsBillAdujestMapper;
import com.infinitus.bms_oa.pojo.BmsBillAdjust;
import com.infinitus.bms_oa.pojo.PlatCode;
import com.infinitus.bms_oa.service.BmsBillAdujestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BmsBillAdjustServiceImpl implements BmsBillAdujestService {
    @Autowired
    private BmsBillAdujestMapper bmsBillAdujestMapper;

    @Override
    public BmsBillAdjust selectAdujestById(Integer id) {

        return bmsBillAdujestMapper.selectAdujestById(id);
    }

    @Override
    public String selectLoginNameById(String id) {
        return bmsBillAdujestMapper.selectLoginNameById(id);
    }

    @Override
    public List<String> selectIdByBillFlag() {
        List<Map<Object,String>> list = bmsBillAdujestMapper.selectIdByBillFlag();
        List<String> stringList = new ArrayList<>();
        if (list.size() > 0) {
            list.stream().forEach(e->{
                stringList.add(e.get("create_id"));
            });
        }
        return stringList;
    }

    @Override
    public List<BmsBillAdjust> selectBillByBillFlag() {
        List<BmsBillAdjust> list = bmsBillAdujestMapper.selectBillByBillFlag();
        List<BmsBillAdjust> list2 =bmsBillAdujestMapper.selectExceptionByBillFlag();
        list.addAll(list2);
        return list;
    }

    @Override
    public boolean updateOA_flag(String oa_flag,String id,String no) {
        boolean a = true;
        if (no.indexOf("IA-") >= 0) {
            a= bmsBillAdujestMapper.updateOA_flag(oa_flag,id);
        }else if(no.indexOf("TZ-")>=0){
            a= bmsBillAdujestMapper.updateOA_flag_ex(oa_flag,id);
        }
        return a;
    }

    @Override
    public boolean updateStatusAndDate( String adj_no, Date approval_dt) {
        if (adj_no.indexOf("IA-") >= 0) {
            bmsBillAdujestMapper.updateBillStatusDate( adj_no, approval_dt);
        } else if(adj_no.indexOf("ST-") >= 0){
            bmsBillAdujestMapper.updateExceptionStatusDate( adj_no, approval_dt);
        }
        return true;
    }

    @Override
    public String getOwnerNameByKey(String owner_key) {
        String name = bmsBillAdujestMapper.getOwnerNameByKey(owner_key);
        if ("".equals(name) || name == null) {
            name="";
        }
        return name;
    }

    @Override
    public String getPlatCodeName(String code) {
        return bmsBillAdujestMapper.getPlatCodeName(code);
    }

    @Override
    public PlatCode getPlatCode(String code) {
        return bmsBillAdujestMapper.getPlatCode(code);
    }

    @Override
    public String getJscName(String wh_code) {
        return bmsBillAdujestMapper.getJscName(wh_code);
    }

    @Override
    public String getGysName(String vendor_code) {
        return bmsBillAdujestMapper.getGysName(vendor_code);
    }

    @Override
    public String getCwkmName(String account_code) {
        return bmsBillAdujestMapper.getCwkmName(account_code);
    }

    @Override
    public List<BmsBillAdjust> getBillListDetail(String code) {
        return bmsBillAdujestMapper.getBillListDetail(code);
    }

    @Override
    public List<BmsBillAdjust> getBillListDetailView(String code, String submit_id) {
        return bmsBillAdujestMapper.getBillListDetailView(code,submit_id);
    }

    @Override
    public boolean updateOA_flag(Integer oa_flag,String code) {
        boolean b = false;
        try {
            bmsBillAdujestMapper.updateOaFlag_all(oa_flag, code);
            bmsBillAdujestMapper.updateOaFlagEx_all(oa_flag, code);
            bmsBillAdujestMapper.updateOaFlagExExempt_all(oa_flag, code);
            b = true;
        } catch (Exception e) {
            log.info("【updateOA_flag】改变oa_flag出错,e:{}",e);
        }
        return b;
    }

    @Override
    public boolean updateLogCode(String code,String create_id) {
        return bmsBillAdujestMapper.updateAdjLogCode(code, create_id);
    }

    @Override
    public boolean updateStatusAndApeDate(String Code, Date approval_dt, String status) {
        boolean b = false;
        try {
            bmsBillAdujestMapper.updateStatusAndApeDate(Code, approval_dt, status);
            bmsBillAdujestMapper.updateExceptionStatusAndApeDate(Code, approval_dt, status);
            bmsBillAdujestMapper.updateExceptionExemptStatusAndApeDate(Code, approval_dt, status);
            b = true;
        } catch (Exception e) {
            log.info("【updateOA_flag】改变oa_flag出错,e:{}",e);
        }
        return b;
    }

}
