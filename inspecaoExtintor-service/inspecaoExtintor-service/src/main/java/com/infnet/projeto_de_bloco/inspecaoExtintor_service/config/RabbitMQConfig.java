package com.infnet.projeto_de_bloco.inspecaoExtintor_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME_INSPECAO = "inspecaoExchange";
    public static final String ROUTING_KEY_INSPECAO = "inspecaoRoutingKey";
    public static final String QUEUE_NAME_INSPECAO = "inspecaoQueue";

    @Bean
    public Exchange declareExchangeInspecao() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME_INSPECAO).durable(true).build();
    }

    @Bean
    public Queue declareQueueInspecao() {
        return QueueBuilder.durable(QUEUE_NAME_INSPECAO).build();
    }

    @Bean
    public Binding declareBindingInspecao(Exchange declareExchangeInspecao, Queue declareQueueInspecao) {
        return BindingBuilder.bind(declareQueueInspecao).to(declareExchangeInspecao).with(ROUTING_KEY_INSPECAO).noargs();
    }


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}