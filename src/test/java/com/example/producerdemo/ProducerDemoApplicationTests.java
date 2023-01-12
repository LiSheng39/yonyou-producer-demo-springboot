package com.example.producerdemo;

import com.alibaba.fastjson.JSON;
import com.example.producerdemo.model.Order;
import com.example.producerdemo.service.SendMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Slf4j
class ProducerDemoApplicationTests {

    @Resource
    SendMessageService sendMessageService;

    @Test
    void contextLoads() {
        //业务对象
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setName("订单名称");
        order.setCode("订单编号");
        /**
         * 消息内容
         */
        Map<String,Object> map = new HashMap<>();
        /**
         * id:和确认机制返回的数据id对应
         * 唯一性，必传，可以是业务对象的数据id
         */
        map.put("id",order.getId());
        /**
         * object:封装业务对象数据
         */
        map.put("object",order);
        /**
         * url:消费者处理完消息后，回调地址
         */
        map.put("url","https://www.baidu.com");
        /**
         * CallType:请求方式   POST
         */
        map.put("CallType","POST");
        /**
         * Authorization:请求token
         */
        map.put("Authorization","****XXX***XXXXXXX");
        String json = JSON.toJSONString(map);
        log.info("信息：" + json);
        sendMessageService.sendMessage(map);
    }

}
