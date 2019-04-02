package com.example.springbootdemo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RabbitMQ 配置
 *
 * @author HP-01
 * @create 2019/4/2 11:04
 */
@Configuration
public class RabbitMqConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 定制化amqp模版      可根据需要定制多个
     *
     *
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调
     * 即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调
     * 即消息发送不到任何一个队列中  ack
     * @param 
     * @return
     * @author HP-01
     * @date 11:13 2019/4/2
     */
    @Bean
    public AmqpTemplate amqpTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        // Jackson2XmlMessageConverter 和 Jackson2JsonMessageConverter 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        // 开启 returnCallback yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认 yml 需要配置 publisher-returns: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 声明 Direct 交换机，支持持久化
     *
     * @param
     * @return
     * @author HP-01
     * @date 11:17 2019/4/2
     */
    @Bean("directExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("DIRECT_EXCHANGE").durable(true).build();
    }

    /**
     * 声明一个队列，支持持久化
     *
     * @param
     * @return
     * @author HP-01
     * @date 11:42 2019/4/2
     */
    @Bean("directQueue")
    public Queue directQueue() {
        return QueueBuilder.durable("DIRECT_QUEUE").build();
    }

    /**
     * 通过绑定键，将指定队列绑定到一个指定的交换机
     *
     * @param queue
	 * @param exchange
     * @return
     * @author HP-01
     * @date 12:42 2019/4/2
     */
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("DIRECT_ROUTING_KEY").noargs();
    }

    /**
     * 主题交换机
     *
     * @param
     * @return
     * @author HP-01
     * @date 16:59 2019/4/2
     */
    @Bean("topicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("TOPIC_EXCHANGE").durable(true).build();
    }

    @Bean("topicQueue")
    public Queue topicQueue() {
        return QueueBuilder.durable("TOPIC_QUEUE").build();
    }

    @Bean
    public Binding topicBinding(@Qualifier("topicQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.ABC").noargs();
    }

    /**
     * 声明 fanout 交换机
     *
     * @param
     * @return
     * @author HP-01
     * @date 12:45 2019/4/2
     */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("FANOUT_EXCHANGE").durable(true).build();
    }

    @Bean("fanoutQueueA")
    public Queue fanoutQueueA() {
        return QueueBuilder.durable("FANOUT_QUEUE_A").build();
    }

    @Bean("fanoutQueueB")
    public Queue fanoutQueueB() {
        return QueueBuilder.durable("FANOUT_QUEUE_B").build();
    }

    /**
     * 绑定队列A 到Fanout 交换机.
     *
     * @param queue
	 * @param fanoutExchange
     * @return
     * @author HP-01
     * @date 12:54 2019/4/2
     */
    @Bean
    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 绑定队列B 到Fanout 交换机.
     * 
     * @param queue
	 * @param fanoutExchange
     * @return
     * @author HP-01
     * @date 13:06 2019/4/2
     */
    @Bean
    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
