package com.infinitus.bms_oa.bms_free.service;

import com.infinitus.bms_oa.bms_free.empty.CouponOrderReport;
import com.infinitus.bms_oa.bms_free.mapper.CouponOrderReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponOrderReportServiceImpl implements CouponOrderReportService {

    @Autowired
    private CouponOrderReportMapper mapper;

    @Override
    public boolean createCouponOrderReport(CouponOrderReport couponOrderReport) {
        return mapper.createCouponOrderReport(couponOrderReport);
    }

}
