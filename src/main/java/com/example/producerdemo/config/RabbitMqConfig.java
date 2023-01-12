package com.example.producerdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:  rabbitmq配置，为了测试暂时放在生产端，后期迁移到消费端
 * @Author:lisheng
 * @Date: Created in  2023/1/12
 */
@Configuration
public class RabbitMqConfig {
    @Value("${exhange.name}")
    private String exchangeName;
    @Value("${queueName.name}")
    private String queueName;
    //1.交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(exchangeName);
    }
    //2.队列
    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }
    //3.bingding
    @Bean
    public Binding bind(){
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }
}
