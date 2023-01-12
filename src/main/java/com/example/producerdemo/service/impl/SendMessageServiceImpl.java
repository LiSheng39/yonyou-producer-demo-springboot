package com.example.producerdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.producerdemo.model.Order;
import com.example.producerdemo.service.SendMessageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONFilter;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 发送消息
 * @Author:lisheng
 * @Date: Created in  2023/1/12
 */
@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {
    @Value("${exhange.name}")
    private String exchangeName;
    @Autowired(required = false)
    RabbitTemplate rabbitTemplate;

    /**
    *@Description: 执行发送信息
    *@Param:
    *@return:
    *@Author: lisheng
    *@Date: 2023/1/12
    */
    @Override
    public void sendMessage(Map<String,Object> map) {
        this.sendMessageToExchange(map);
    }

    /**
    *@Description: 发送消息
    *@Param:
    *@return:
    *@Author: lisheng
    *@Date: 2023/1/12
    */
    private void sendMessageToExchange(Map<String,Object> map){
        //1.交换机
        String exhangeName = exchangeName;
        //2.路由
        String routingkey = "";
        rabbitTemplate.convertAndSend(exhangeName,routingkey, JSON.toJSONString(map),new CorrelationData(map.get("id").toString()));
    }

    /**
    *@Description: 消息确认机制
    *@Param:
    *@return:
    *@Author: lisheng
    *@Date: 2023/1/12
    */
    @PostConstruct
    public void regCallBack(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("确认机制");
                String id = correlationData.getId();
                log.info("确认机制返回的信息：" + id);
                //b参数为true 说明消息已收到
                if (!b){
                    log.info("未收到信息，请进行后续处理【比如重推机制】");
                }else {
                    log.info("成功收到信息，进行后续处理");
                }
            }
        });
    }

}
