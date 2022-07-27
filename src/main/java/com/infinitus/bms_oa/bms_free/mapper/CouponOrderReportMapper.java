package com.infinitus.bms_oa.bms_free.mapper;

import com.infinitus.bms_oa.bms_free.empty.CouponOrderReport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponOrderReportMapper {
    boolean createCouponOrderReport(CouponOrderReport couponOrderReport);
}
