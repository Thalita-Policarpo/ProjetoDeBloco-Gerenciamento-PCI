package com.infnet.projeto_de_bloco.inspecaoExtintor_service.service;

import com.infnet.projeto_de_bloco.inspecaoExtintor_service.client.ExtintorClient;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto.ExtintorDTO;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto.InspecaoExtintorDTO;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.exception.InspecaoNotFoundException;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.model.InspecaoExtintor;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.repository.InspecaoExtintorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InspecaoExtintorService {

    private final InspecaoExtintorRepository inspecaoRepository;
    private final ExtintorClient extintorClient;
    private final RabbitTemplate rabbitTemplate;

    public InspecaoExtintorDTO realizarInspecao(InspecaoExtintorDTO inspecaoDTO, int numeroControleInterno) {
        // Verificar se o extintor existe usando o número de controle interno
        ExtintorDTO extintor = extintorClient.getByNumeroControleInterno(numeroControleInterno);
        if (extintor == null) {
            throw new IllegalArgumentException("Extintor não encontrado com Número de Controle Interno: " + numeroControleInterno);
        }

        // Criar nova inspeção e associar com o extintor encontrado
        InspecaoExtintor inspecao = new InspecaoExtintor();
        BeanUtils.copyProperties(inspecaoDTO, inspecao);
        inspecao.setDataInspecao(LocalDate.now());
        inspecao.setExtintorId(extintor.getId());

        // Definir status e observações automaticamente com base nas respostas booleanas
        String status = "Conforme";
        StringBuilder observacoes = new StringBuilder();

        if (!inspecao.isSinalizado()) {
            status = "Não Conforme";
            observacoes.append("Extintor não sinalizado; ");
        }
        if (!inspecao.isDesobstruido()) {
            status = "Não Conforme";
            observacoes.append("Extintor obstruído; ");
        }
        if (!inspecao.isManometroPressaoAdequada()) {
            status = "Não Conforme";
            observacoes.append("Manômetro com pressão inadequada; ");
        }
        if (!inspecao.isGatilhoBoasCondicoes()) {
            status = "Não Conforme";
            observacoes.append("Gatilho em más condições; ");
        }
        if (!inspecao.isMangoteBoasCondicoes()) {
            status = "Não Conforme";
            observacoes.append("Mangote em más condições; ");
        }
        if (!inspecao.isRotuloPinturaBoasCondicoes()) {
            status = "Não Conforme";
            observacoes.append("Rótulo ou pintura em más condições; ");
        }
        if (!inspecao.isSuporteBoasCondicoes()) {
            status = "Não Conforme";
            observacoes.append("Suporte em más condições; ");
        }
        if (!inspecao.isLacreIntacto()) {
            status = "Não Conforme";
            observacoes.append("Lacre violado; ");
        }
        if (LocalDate.now().isAfter(extintor.getDataVencimento().atEndOfMonth())) {
            status = "Não Conforme";
            observacoes.append("Extintor com data de vencimento expirada; ");
        }
        if (Year.now().isAfter(extintor.getProximoTesteHidrostatico())) {
            status = "Não Conforme";
            observacoes.append("Extintor com teste hidrostático vencido; ");
        }

        inspecao.setStatus(status);
        inspecao.setObservacoes(observacoes.toString());

        // Salvar inspeção
        InspecaoExtintor savedInspecao = inspecaoRepository.save(inspecao);
        log.info("Inspeção realizada com ID: {}", savedInspecao.getId());

        // **Enviar mensagem para o serviço de extintores para vincular o ID da inspeção ao extintor**
        try {
            Map<String, Integer> message = new HashMap<>();
            message.put("extintorId", extintor.getId());
            message.put("inspecaoId", savedInspecao.getId());

            rabbitTemplate.convertAndSend("inspecaoExchange", "inspecaoRoutingKey", message);
            log.info("Mensagem enviada para vincular Inspecao ID {} ao Extintor ID {}", savedInspecao.getId(), extintor.getId());
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para RabbitMQ: {}", e.getMessage());
            throw new IllegalStateException("Erro ao enviar mensagem para o serviço de extintores");
        }


        // Retornar a inspeção realizada
        InspecaoExtintorDTO savedInspecaoDTO = new InspecaoExtintorDTO();
        BeanUtils.copyProperties(savedInspecao, savedInspecaoDTO);
        return savedInspecaoDTO;
    }

    public InspecaoExtintor getInspecaoById(Integer id) {
        return inspecaoRepository.findById(id)
                .orElseThrow(() -> new InspecaoNotFoundException("Inspeção não encontrada com ID: " + id));
    }

    public List<InspecaoExtintor> getInspecoesByExtintorId(Integer extintorId) {
        List<InspecaoExtintor> inspecoes = inspecaoRepository.findByExtintorId(extintorId);
        if (inspecoes.isEmpty()) {
            throw new InspecaoNotFoundException("Nenhuma inspeção encontrada para o Extintor ID: " + extintorId);
        }
        return inspecoes;
    }

    public List<InspecaoExtintor> getInspecoesByNumeroControleInternoExtintor(int numeroControleInterno) {
        ExtintorDTO extintor = extintorClient.getByNumeroControleInterno(numeroControleInterno);
        if (extintor == null) {
            throw new IllegalArgumentException("Extintor não encontrado com Número de Controle Interno: " + numeroControleInterno);
        }

        // Buscar todas as inspeções associadas ao extintor encontrado
        return getInspecoesByExtintorId(extintor.getId());
    }

    public List<InspecaoExtintor> getAllInspecoes() {
        List<InspecaoExtintor> inspecoes = inspecaoRepository.findAll();
        if (inspecoes.isEmpty()) {
            throw new InspecaoNotFoundException("Nenhuma inspeção encontrada.");
        }
        return inspecoes;
    }

    public void deleteById(Integer id) {
        inspecaoRepository.findById(id)
                .orElseThrow(() -> new InspecaoNotFoundException("Inspeção não encontrada com ID: " + id));
        inspecaoRepository.deleteById(id);
        log.info("Inspeção deletada com ID: {}", id);
    }

    public InspecaoExtintorDTO updateById(Integer id, InspecaoExtintorDTO inspecaoDTO) {
        InspecaoExtintor inspecao = inspecaoRepository.findById(id)
                .orElseThrow(() -> new InspecaoNotFoundException("Inspeção não encontrada com ID: " + id));
        BeanUtils.copyProperties(inspecaoDTO, inspecao, "id");
        InspecaoExtintor updatedInspecao = inspecaoRepository.save(inspecao);
        log.info("Inspeção atualizada com ID: {}", updatedInspecao.getId());

        InspecaoExtintorDTO updatedInspecaoDTO = new InspecaoExtintorDTO();
        BeanUtils.copyProperties(updatedInspecao, updatedInspecaoDTO);
        return updatedInspecaoDTO;
    }
}