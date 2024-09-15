package com.infnet.projeto_de_bloco.inspecaoExtintor_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InspecaoExtintor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataInspecao;
    private boolean sinalizado;
    private boolean desobstruido;
    private boolean manometroPressaoAdequada;
    private boolean gatilhoBoasCondicoes;
    private boolean mangoteBoasCondicoes;
    private boolean rotuloPinturaBoasCondicoes;
    private boolean suporteBoasCondicoes;
    private boolean lacreIntacto;
    private String status;
    private String observacoes;

    private Integer extintorId;
}