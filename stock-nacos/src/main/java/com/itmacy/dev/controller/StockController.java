package com.itmacy.dev.controller;

import com.itmacy.dev.alarm.SwAlarmDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: itmacy
 * date: 2022/3/28
 */
@RestController
@RequestMapping("/stock")
public class StockController {
    @Value("${server.port}")
    String port;

    @GetMapping("deduct")
    public String deduct(){
//        模拟服务出现异常
        int k = 1/0;
        return "扣除库存成功:" + port;
    }

    @GetMapping("get")
    public String get(){
        return "查询库存成功:" + port;
    }


    /**
     * 测试skywalking告警通知
     * @return
     * @throws InterruptedException
     */
    @GetMapping("testAlarm")
    public String testAlarm() throws InterruptedException {
        Thread.sleep(1000 * 10);
        return "查询库存成功:" + port;
    }

    /**
     * 提供给skywalking告警的回调接口
     * @param alarmList
     */
    @PostMapping("/receive")
    public void receive(@RequestBody List<SwAlarmDTO> alarmList){
        System.out.println("================告警通知=================");
        System.out.println(getContent(alarmList));
        System.out.println("===============告警通知END=================");
    }

    private String getContent(List<SwAlarmDTO> alarmList) {
        StringBuilder sb = new StringBuilder();
        for (SwAlarmDTO dto : alarmList) {
            sb.append("scopeId: ").append(dto.getScopeId())
            .append("\nscope: ").append(dto.getScope())
             .append("\n目标 Scope 的实体名称: ").append(dto.getName())
             .append("\nScope 实体的 ID: ").append(dto.getId0())
             .append("\nid1: ").append(dto.getId1())
             .append("\n告警规则名称: ").append(dto.getRuleName())
             .append("\n告警消息内容: ").append(dto.getAlarmMessage())
             .append("\n告警时间: ").append(dto.getStartTime())
             .append("\n\n‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐\n\n");
             }
         return sb.toString();
    }

}
