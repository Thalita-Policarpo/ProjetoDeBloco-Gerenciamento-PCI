package com.infnet.projeto_de_bloco.extintor_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Configurações de Inspeção
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

    // Configurações de Histórico
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

    // Configuração do conversor de mensagens para JSON (Jackson)
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configuração do RabbitTemplate para enviar mensagens
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
