package com.infnet.projeto_de_bloco.historicoExtintor_service.service;

import com.infnet.projeto_de_bloco.historicoExtintor_service.dto.HistoricoExtintorDTO;
import com.infnet.projeto_de_bloco.historicoExtintor_service.model.HistoricoExtintor;
import com.infnet.projeto_de_bloco.historicoExtintor_service.repository.HistoricoExtintorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HistoricoExtintorService {

    private final HistoricoExtintorRepository historicoRepository;

    public HistoricoExtintorService(HistoricoExtintorRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @RabbitListener(queues = "historicoQueue")
    public void salvarHistorico(HistoricoExtintorDTO historicoExtintorDTO) {
        try {
            // Verificar se a mensagem contém os campos necessários
            if (historicoExtintorDTO.getExtintorId() == null || historicoExtintorDTO.getTipoOperacao() == null) {
                log.error("Mensagem inválida: extintorId ou tipoOperacao está faltando.");
                return;
            }

            // Criar um novo objeto de histórico
            HistoricoExtintor historicoExtintor = new HistoricoExtintor();
            BeanUtils.copyProperties(historicoExtintorDTO, historicoExtintor);
            historicoExtintor.setDataAlteracao(LocalDateTime.now());

            // Salvar no banco de dados
            historicoRepository.save(historicoExtintor);
            log.info("Histórico salvo para Extintor ID: {} com operação: {}", historicoExtintor.getExtintorId(), historicoExtintorDTO.getTipoOperacao());
        } catch (Exception e) {
            log.error("Erro ao salvar histórico: {}", e.getMessage(), e);
        }
    }

    public List<HistoricoExtintor> getAll() {
        return historicoRepository.findAll();
    }

    public List<HistoricoExtintor> getByNumeroCilindro(String numeroCilindro) {
        return historicoRepository.findAll().stream()
                .filter(historico -> historico.getNumeroCilindro().equals(numeroCilindro))
                .collect(Collectors.toList());
    }
}