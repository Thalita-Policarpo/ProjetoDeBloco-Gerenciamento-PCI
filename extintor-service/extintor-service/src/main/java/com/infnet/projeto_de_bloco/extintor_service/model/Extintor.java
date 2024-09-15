package com.infnet.projeto_de_bloco.extintor_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Extintor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int numeroControleInterno;
    private String numeroCilindro;
    private String numeroSeloInmetro;
    private String cargaExtintora;
    private String capacidade;
    private YearMonth dataVencimento;
    private Year proximoTesteHidrostatico;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> inspecoesId = new ArrayList<>();

}