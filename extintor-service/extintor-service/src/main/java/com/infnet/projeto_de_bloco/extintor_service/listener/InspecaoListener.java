package com.infnet.projeto_de_bloco.extintor_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.projeto_de_bloco.extintor_service.config.RabbitMQConfig;
import com.infnet.projeto_de_bloco.extintor_service.service.ExtintorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InspecaoListener implements ChannelAwareMessageListener {

    private final ExtintorService extintorService;

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_INSPECAO)
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            // Log da mensagem recebida
            log.info("Mensagem recebida: {}", new String(message.getBody()));

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Integer> messageData = objectMapper.readValue(message.getBody(), Map.class);

            Integer extintorId = messageData.get("extintorId");
            Integer inspecaoId = messageData.get("inspecaoId");

            // Log dos IDs recebidos
            log.info("Processando mensagem: Extintor ID {}, Inspecao ID {}", extintorId, inspecaoId);

            // Vincular inspeção ao extintor
            extintorService.adicionarInspecao(extintorId, inspecaoId);
            log.info("Mensagem processada: Extintor ID {} recebeu Inspecao ID {}", extintorId, inspecaoId);

            // Confirmação manual da mensagem processada com sucesso
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IllegalArgumentException e) {
            log.error("Erro ao processar mensagem: {}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar mensagem: {}", e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}