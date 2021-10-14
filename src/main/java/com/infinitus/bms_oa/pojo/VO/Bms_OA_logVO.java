package com.infinitus.bms_oa.pojo.VO;

import lombok.Data;

import java.util.Date;

@Data
public class Bms_OA_logVO {
    private String id;
    private String code;//主单号 由时间戳生成，用于同步过去OA系统对应单号
    private String oa_flag;//同步标志，0：未同步；2：已同步；4：同步失败；6：无需同步
    private String bill_code;//将需要合并的code放一起
    private Date summit_dt;//提交日期
    private Date syn_dt;//同步日期
}
