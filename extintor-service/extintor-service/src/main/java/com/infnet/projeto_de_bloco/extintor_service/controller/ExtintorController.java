package com.infnet.projeto_de_bloco.extintor_service.controller;

import com.infnet.projeto_de_bloco.extintor_service.dto.ExtintorDTO;
import com.infnet.projeto_de_bloco.extintor_service.model.Extintor;
import com.infnet.projeto_de_bloco.extintor_service.service.ExtintorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extintor")
@Slf4j
public class ExtintorController {

    @Autowired
    private ExtintorService extintorService;

    // Método para cadastrar Extintor (usando ExtintorDTO)
    @PostMapping
    @Operation(summary = "Cadastrar Extintor", description = "Cria um novo extintor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extintor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ExtintorDTO> add(@RequestBody ExtintorDTO extintorDTO) {
        ExtintorDTO savedExtintor = extintorService.add(extintorDTO);
        return ResponseEntity.ok(savedExtintor);
    }

    // Método para buscar Extintor por ID (retornando entidade Extintor com inspeções)
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Extintor por ID", description = "Retorna um extintor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extintor encontrado"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<Extintor> getById(@PathVariable Integer id) {
        Extintor extintor = extintorService.getById(id);
        return ResponseEntity.ok(extintor);
    }

    // Método para buscar Extintor por Número de Controle Interno (retornando entidade Extintor com inspeções)
    @GetMapping("/controleInterno/{numeroControleInterno}")
    @Operation(summary = "Buscar Extintor por Número de Controle Interno", description = "Retorna um extintor pelo Número de Controle Interno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extintor encontrado"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<Extintor> getByNumeroControleInterno(@PathVariable int numeroControleInterno) {
        Extintor extintor = extintorService.getByNumeroControleInterno(numeroControleInterno);
        return ResponseEntity.ok(extintor);
    }

    // Método para listar todos os Extintores (retornando entidades Extintor com inspeções)
    @GetMapping
    @Operation(summary = "Listar Todos os Extintores", description = "Retorna uma lista de todos os extintores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de extintores retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum extintor encontrado")
    })
    public ResponseEntity<List<Extintor>> getAll() {
        List<Extintor> extintores = extintorService.getAll();
        return ResponseEntity.ok(extintores);
    }

    // Método para atualizar Extintor por ID (usando ExtintorDTO)
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Extintor por ID", description = "Atualiza um extintor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extintor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<ExtintorDTO> updateById(@PathVariable Integer id, @RequestBody ExtintorDTO extintorDTO) {
        ExtintorDTO updatedExtintor = extintorService.updateById(id, extintorDTO);
        return ResponseEntity.ok(updatedExtintor);
    }

    // Método para atualizar Extintor por Número de Controle Interno (usando ExtintorDTO)
    @PutMapping("/controleInterno/{numeroControleInterno}")
    @Operation(summary = "Atualizar Extintor por Número de Controle Interno", description = "Atualiza um extintor pelo Número de Controle Interno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extintor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<ExtintorDTO> updateByNumeroControleInterno(@PathVariable int numeroControleInterno, @RequestBody ExtintorDTO extintorDTO) {
        ExtintorDTO updatedExtintor = extintorService.updateByNumeroControleInterno(numeroControleInterno, extintorDTO);
        return ResponseEntity.ok(updatedExtintor);
    }

    // Método para deletar Extintor por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Extintor por ID", description = "Deleta um extintor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Extintor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        extintorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método para deletar Extintor por Número de Controle Interno
    @DeleteMapping("/controleInterno/{numeroControleInterno}")
    @Operation(summary = "Deletar Extintor por Número de Controle Interno", description = "Deleta um extintor pelo Número de Controle Interno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Extintor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extintor não encontrado")
    })
    public ResponseEntity<Void> deleteByNumeroControleInterno(@PathVariable int numeroControleInterno) {
        extintorService.deleteByNumeroControleInterno(numeroControleInterno);
        return ResponseEntity.noContent().build();
    }
}
