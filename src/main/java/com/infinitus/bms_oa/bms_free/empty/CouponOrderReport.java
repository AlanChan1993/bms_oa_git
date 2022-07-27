package com.infinitus.bms_oa.bms_free.empty;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponOrderReport {
         private String receiveName;//收货人姓名
         private String  couponNumber;//优惠券编号
         private String  senderAddress;//寄件人地址
         private String   useTime; //使用时间
         private String  senderName; // 寄件人姓名
         private String  enableTime;//优惠券生效日期
         private BigDecimal weight; //重量 单位kg
         private String  expressName;//快递公司
         private BigDecimal deductAmount;// 优惠劵抵扣金额 分
         private String  disableTime; //优惠券失效时间
         private String receiveAddress; //收货人地址
         private String expressNumber;//快递单号
         private String shopId; //店铺id
         private String  purchaseNumber;//优惠券采购编号
         private BigDecimal  price;//总运费金额 分
}
