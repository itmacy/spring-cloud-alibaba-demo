package com.itmacy.dev.alarm;

import lombok.Data;

/**
 * @author: itmacy
 * @date: 2022/4/5
 */
@Data
public class SwAlarmDTO {
    /**
     * 11 "scopeId": 1,
     * 12 "scope": "SERVICE",
     * 13 "name": "serviceB",
     * 14 "id0": "23",
     * 15 "id1": "",
     * 16 "ruleName": "service_resp_time_rule",
     * 17 "alarmMessage": "alarmMessage yyy",
     * 18 "startTime": 1560524171000
     */
    private String scopeId;
    private String scope;
    private String name;
    private String id0;
    private String id1;
    private String ruleName;
    private String alarmMessage;
    private Long startTime;
}
