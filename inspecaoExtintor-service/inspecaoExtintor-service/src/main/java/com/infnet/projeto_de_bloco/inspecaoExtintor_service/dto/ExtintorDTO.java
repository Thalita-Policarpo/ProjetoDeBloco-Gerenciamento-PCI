package com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto;

import lombok.*;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtintorDTO {
    private Integer id;
    private int numeroControleInterno;
    private String numeroCilindro;
    private String numeroSeloInmetro;
    private String cargaExtintora;
    private String capacidade;
    private YearMonth dataVencimento;
    private Year proximoTesteHidrostatico;
    private List<Integer> inspecoesId;
}