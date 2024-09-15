package com.infnet.projeto_de_bloco.inspecaoExtintor_service.repository;

import com.infnet.projeto_de_bloco.inspecaoExtintor_service.model.InspecaoExtintor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspecaoExtintorRepository extends JpaRepository<InspecaoExtintor, Integer> {
    List<InspecaoExtintor> findByExtintorId(Integer extintorId);
}