package com.infnet.projeto_de_bloco.extintor_service.service;

import com.infnet.projeto_de_bloco.extintor_service.dto.ExtintorDTO;
import com.infnet.projeto_de_bloco.extintor_service.model.Extintor;
import com.infnet.projeto_de_bloco.extintor_service.repository.ExtintorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtintorService {

    private final ExtintorRepository extintorRepository;
    private final RabbitTemplate rabbitTemplate;

    public ExtintorDTO add(ExtintorDTO extintorDTO) {
        // Validação para garantir que não exista um extintor com o mesmo número de controle interno
        Optional<Extintor> existingExtintor = extintorRepository.findByNumeroControleInterno(extintorDTO.getNumeroControleInterno());
        if (existingExtintor.isPresent()) {
            throw new IllegalArgumentException("Já existe um extintor cadastrado com o número de controle interno: " + extintorDTO.getNumeroControleInterno());
        }

        // Criação do novo extintor
        Extintor extintor = new Extintor();
        BeanUtils.copyProperties(extintorDTO, extintor);
        Extintor savedExtintor = extintorRepository.save(extintor);
        log.info("Extintor criado com ID: {}", savedExtintor.getId());

        // Enviar mensagem para o histórico
        enviarMensagemHistorico(savedExtintor, "CREATE");

        return converterParaDTO(savedExtintor);
    }

    public Extintor getById(Integer id) {
        return extintorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com ID: " + id));
    }

    public Extintor getByNumeroControleInterno(int numeroControleInterno) {
        return extintorRepository.findByNumeroControleInterno(numeroControleInterno)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com Número de Controle Interno: " + numeroControleInterno));
    }

    public List<Extintor> getAll() {
        return extintorRepository.findAll();
    }

    public ExtintorDTO updateById(Integer id, ExtintorDTO extintorDTO) {
        Extintor extintor = extintorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com ID: " + id));
        BeanUtils.copyProperties(extintorDTO, extintor, "id");
        Extintor updatedExtintor = extintorRepository.save(extintor);
        log.info("Extintor atualizado com ID: {}", updatedExtintor.getId());

        // Enviar mensagem para o histórico
        enviarMensagemHistorico(updatedExtintor, "UPDATE");

        return converterParaDTO(updatedExtintor);
    }

    public ExtintorDTO updateByNumeroControleInterno(int numeroControleInterno, ExtintorDTO extintorDTO) {
        Extintor extintor = extintorRepository.findByNumeroControleInterno(numeroControleInterno)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com Número de Controle Interno: " + numeroControleInterno));
        BeanUtils.copyProperties(extintorDTO, extintor, "id");
        Extintor updatedExtintor = extintorRepository.save(extintor);
        log.info("Extintor atualizado com Número de Controle Interno: {}", updatedExtintor.getNumeroControleInterno());

        // Enviar mensagem para o histórico
        enviarMensagemHistorico(updatedExtintor, "UPDATE");

        return converterParaDTO(updatedExtintor);
    }

    public void deleteById(Integer id) {
        Extintor extintor = extintorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com ID: " + id));
        extintorRepository.deleteById(id);
        log.info("Extintor deletado com ID: {}", id);

        // Enviar mensagem para o histórico
        enviarMensagemHistorico(extintor, "DELETE");
    }

    public void deleteByNumeroControleInterno(int numeroControleInterno) {
        Extintor extintor = extintorRepository.findByNumeroControleInterno(numeroControleInterno)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com Número de Controle Interno: " + numeroControleInterno));
        extintorRepository.delete(extintor);
        log.info("Extintor deletado com Número de Controle Interno: {}", numeroControleInterno);

        // Enviar mensagem para o histórico
        enviarMensagemHistorico(extintor, "DELETE");
    }

    public void adicionarInspecao(Integer extintorId, Integer inspecaoId) {
        Extintor extintor = extintorRepository.findById(extintorId)
                .orElseThrow(() -> new IllegalArgumentException("Extintor não encontrado com ID: " + extintorId));

        List<Integer> inspecoesAtualizadas = extintor.getInspecoesId() != null ? extintor.getInspecoesId() : new ArrayList<>();
        inspecoesAtualizadas.add(inspecaoId);

        extintor.setInspecoesId(inspecoesAtualizadas);
        extintorRepository.save(extintor);

        log.info("Inspeção ID: {} foi adicionada ao Extintor ID: {}. Inspeções agora: {}", inspecaoId, extintorId, extintor.getInspecoesId());
    }

    // Método privado para enviar a mensagem ao histórico
    private void enviarMensagemHistorico(Extintor extintor, String tipoOperacao) {
        Map<String, Object> message = new HashMap<>();
        message.put("extintorId", extintor.getId());
        message.put("tipoOperacao", tipoOperacao);
        message.put("numeroControleInterno", extintor.getNumeroControleInterno());
        message.put("numeroCilindro", extintor.getNumeroCilindro());
        message.put("numeroSeloInmetro", extintor.getNumeroSeloInmetro());
        message.put("cargaExtintora", extintor.getCargaExtintora());
        message.put("capacidade", extintor.getCapacidade());
        message.put("dataVencimento", extintor.getDataVencimento());
        message.put("proximoTesteHidrostatico", extintor.getProximoTesteHidrostatico());

        rabbitTemplate.convertAndSend("historicoExchange", "historicoRoutingKey", message);
    }

    // Método privado para converter um Extintor em ExtintorDTO
    private ExtintorDTO converterParaDTO(Extintor extintor) {
        ExtintorDTO extintorDTO = new ExtintorDTO();
        BeanUtils.copyProperties(extintor, extintorDTO);
        return extintorDTO;
    }
}
