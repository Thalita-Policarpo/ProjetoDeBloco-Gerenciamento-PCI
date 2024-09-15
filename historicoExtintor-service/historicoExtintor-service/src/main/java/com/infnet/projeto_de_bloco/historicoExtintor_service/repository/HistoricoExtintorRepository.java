package com.infnet.projeto_de_bloco.historicoExtintor_service.repository;

import com.infnet.projeto_de_bloco.historicoExtintor_service.model.HistoricoExtintor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoExtintorRepository extends JpaRepository<HistoricoExtintor, Integer> {
    List<HistoricoExtintor> findByExtintorId(Integer extintorId);
}