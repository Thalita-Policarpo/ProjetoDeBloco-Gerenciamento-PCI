package com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspecaoExtintorDTO {

    @Schema(description = "Indica se o extintor está sinalizado", example = "true")
    private boolean sinalizado;

    @Schema(description = "Indica se o extintor está desobstruído", example = "true")
    private boolean desobstruido;

    @Schema(description = "Indica se o manômetro está com pressão adequada", example = "true")
    private boolean manometroPressaoAdequada;

    @Schema(description = "Indica se o gatilho está em boas condições", example = "true")
    private boolean gatilhoBoasCondicoes;

    @Schema(description = "Indica se o mangote está em boas condições", example = "true")
    private boolean mangoteBoasCondicoes;

    @Schema(description = "Indica se o rótulo e a pintura estão em boas condições", example = "true")
    private boolean rotuloPinturaBoasCondicoes;

    @Schema(description = "Indica se o suporte está em boas condições", example = "true")
    private boolean suporteBoasCondicoes;

    @Schema(description = "Indica se o lacre está intacto", example = "true")
    private boolean lacreIntacto;
}