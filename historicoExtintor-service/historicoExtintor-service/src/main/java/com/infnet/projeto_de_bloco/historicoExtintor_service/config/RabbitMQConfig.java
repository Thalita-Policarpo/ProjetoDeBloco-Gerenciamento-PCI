package com.infnet.projeto_de_bloco.historicoExtintor_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME_HISTORICO = "historicoExchange";
    public static final String ROUTING_KEY_HISTORICO = "historicoRoutingKey";
    public static final String QUEUE_NAME_HISTORICO = "historicoQueue";

    @Bean
    public Exchange declareExchangeHistorico() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME_HISTORICO).durable(true).build();
    }

    @Bean
    public Queue declareQueueHistorico() {
        return QueueBuilder.durable(QUEUE_NAME_HISTORICO).build();
    }

    @Bean
    public Binding declareBindingHistorico(Exchange declareExchangeHistorico, Queue declareQueueHistorico) {
        return BindingBuilder.bind(declareQueueHistorico).to(declareExchangeHistorico).with(ROUTING_KEY_HISTORICO).noargs();
    }

    // Conversor de mensagens para JSON
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