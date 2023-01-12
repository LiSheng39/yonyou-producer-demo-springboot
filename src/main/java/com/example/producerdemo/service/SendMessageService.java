package com.example.producerdemo.service;

import com.example.producerdemo.model.Order;

import java.util.Map;

/**
 * @Description:
 * @Author:lisheng
 * @Date: Created in  2023/1/12
 */
public interface SendMessageService {
    public void sendMessage(Map<String,Object> map);
}
