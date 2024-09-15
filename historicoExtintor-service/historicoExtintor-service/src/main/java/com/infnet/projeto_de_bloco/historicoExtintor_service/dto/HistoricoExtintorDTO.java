package com.infnet.projeto_de_bloco.historicoExtintor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Year;
import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoExtintorDTO {
    private int numeroControleInterno;
    private String numeroCilindro;
    private String numeroSeloInmetro;
    private String cargaExtintora;
    private String capacidade;
    private YearMonth dataVencimento;
    private Year proximoTesteHidrostatico;
    private String tipoOperacao; // CREATE, UPDATE, DELETE
    private Integer extintorId;
}