package com.infnet.projeto_de_bloco.extintor_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtintorDTO {
    private int numeroControleInterno;
    private String numeroCilindro;
    private String numeroSeloInmetro;
    private String cargaExtintora;
    private String capacidade;

    @Schema(description = "Data de vencimento no formato YYYY-MM", example = "2024-09")
    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth dataVencimento;

    @Schema(description = "Data do próximo teste hidrostático no formato YYYY", example = "2024")
    @JsonFormat(pattern = "yyyy")
    private Year proximoTesteHidrostatico;
}
