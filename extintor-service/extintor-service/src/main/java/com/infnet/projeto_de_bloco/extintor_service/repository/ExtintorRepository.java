package com.infnet.projeto_de_bloco.extintor_service.repository;

import com.infnet.projeto_de_bloco.extintor_service.model.Extintor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExtintorRepository extends JpaRepository<Extintor, Integer> {
    Optional<Extintor> findByNumeroControleInterno(int numeroControleInterno);
}