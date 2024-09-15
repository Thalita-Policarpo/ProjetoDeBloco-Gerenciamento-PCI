package com.infnet.projeto_de_bloco.historicoExtintor_service.controller;

import com.infnet.projeto_de_bloco.historicoExtintor_service.model.HistoricoExtintor;
import com.infnet.projeto_de_bloco.historicoExtintor_service.service.HistoricoExtintorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico")
@Slf4j
@RequiredArgsConstructor
public class HistoricoExtintorController {

    private final HistoricoExtintorService historicoService;

    @GetMapping
    @Operation(summary = "Listar Todo o Histórico", description = "Retorna uma lista de todas as operações registradas no histórico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de histórico retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum histórico encontrado")
    })
    public ResponseEntity<List<HistoricoExtintor>> getAll() {
        List<HistoricoExtintor> historico = historicoService.getAll();
        return ResponseEntity.ok(historico);
    }


    @GetMapping("/filtro/cilindro")
    @Operation(summary = "Buscar Histórico por Número de Cilindro", description = "Retorna o histórico de extintores com o número de cilindro especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
            @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
    })
    public ResponseEntity<List<HistoricoExtintor>> getByNumeroCilindro(@RequestParam String numeroCilindro) {
        List<HistoricoExtintor> historico = historicoService.getByNumeroCilindro(numeroCilindro);
        return ResponseEntity.ok(historico);
    }
}
