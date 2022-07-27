package com.infinitus.bms_oa.bms_free.empty;

import lombok.Data;

@Data
public class PlatformType {
    private String useTimeStart;//起始时间
    private String useTimeEnd;//结束时间
    private final String platformType = "1";//所属平台
}
