package com.example.springbootdemo.rabbitmq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 消息接口
 *
 * @author HP-01
 * @create 2019/4/2 13:15
 */
@RestController
@RequestMapping("/rabbit")
public class SenderController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *  测试广播模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/fanout")
    public ResponseEntity send(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
        return ResponseEntity.ok();
    }

    /**
     *  测试Direct模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/direct")
    public ResponseEntity direct(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
        return ResponseEntity.ok();
    }
}
