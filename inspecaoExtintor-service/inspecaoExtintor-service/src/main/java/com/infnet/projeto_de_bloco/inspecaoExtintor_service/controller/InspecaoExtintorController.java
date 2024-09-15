package com.infnet.projeto_de_bloco.inspecaoExtintor_service.controller;

import com.infnet.projeto_de_bloco.inspecaoExtintor_service.model.InspecaoExtintor;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto.InspecaoExtintorDTO;
import com.infnet.projeto_de_bloco.inspecaoExtintor_service.service.InspecaoExtintorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inspecao")
@Slf4j
public class InspecaoExtintorController {

    @Autowired
    private InspecaoExtintorService inspecaoService;

    @PostMapping
    @Operation(summary = "Realizar Inspeção", description = "Realiza uma nova inspeção em um extintor, validando o número de controle interno do extintor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspeção realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<InspecaoExtintorDTO> realizarInspecao(
            @RequestBody InspecaoExtintorDTO inspecaoDTO,
            @RequestParam int numeroControleInterno) {
        InspecaoExtintorDTO savedInspecao = inspecaoService.realizarInspecao(inspecaoDTO, numeroControleInterno);
        return ResponseEntity.ok(savedInspecao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Inspeção por ID", description = "Retorna uma inspeção pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspeção encontrada"),
            @ApiResponse(responseCode = "404", description = "Inspeção não encontrada")
    })
    public ResponseEntity<InspecaoExtintor> getById(@PathVariable Integer id) {
        InspecaoExtintor inspecao = inspecaoService.getInspecaoById(id);
        return ResponseEntity.ok(inspecao);
    }

    @GetMapping("/extintor/{extintorId}")
    @Operation(summary = "Buscar Inspeções por Extintor ID", description = "Retorna as inspeções de um extintor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspeções encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma inspeção encontrada para o extintor")
    })
    public ResponseEntity<List<InspecaoExtintor>> getByExtintorId(@PathVariable Integer extintorId) {
        List<InspecaoExtintor> inspecoes = inspecaoService.getInspecoesByExtintorId(extintorId);
        return ResponseEntity.ok(inspecoes);
    }

    @GetMapping("/controleInterno/{numeroControleInterno}")
    @Operation(summary = "Buscar Inspeções por Número de Controle Interno", description = "Retorna as inspeções de um extintor pelo Número de Controle Interno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspeções encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma inspeção encontrada para o extintor")
    })
    public ResponseEntity<List<InspecaoExtintor>> getByNumeroControleInternoExtintor(@PathVariable int numeroControleInterno) {
        List<InspecaoExtintor> inspecoes = inspecaoService.getInspecoesByNumeroControleInternoExtintor(numeroControleInterno);
        return ResponseEntity.ok(inspecoes);
    }

    @GetMapping
    @Operation(summary = "Listar Todas as Inspeções", description = "Retorna uma lista de todas as inspeções")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de inspeções retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma inspeção encontrada")
    })
    public ResponseEntity<List<InspecaoExtintor>> getAll() {
        List<InspecaoExtintor> inspecoes = inspecaoService.getAllInspecoes();
        return ResponseEntity.ok(inspecoes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Inspeção por ID", description = "Atualiza uma inspeção pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspeção atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inspeção não encontrada")
    })
    public ResponseEntity<InspecaoExtintorDTO> updateById(@PathVariable Integer id, @RequestBody InspecaoExtintorDTO inspecaoDTO) {
        InspecaoExtintorDTO updatedInspecao = inspecaoService.updateById(id, inspecaoDTO);
        return ResponseEntity.ok(updatedInspecao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Inspeção por ID", description = "Deleta uma inspeção pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inspeção deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inspeção não encontrada")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        inspecaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}