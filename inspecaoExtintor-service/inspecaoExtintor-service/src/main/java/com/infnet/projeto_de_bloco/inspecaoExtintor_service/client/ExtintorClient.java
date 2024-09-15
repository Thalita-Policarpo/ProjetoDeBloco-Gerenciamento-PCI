package com.infnet.projeto_de_bloco.inspecaoExtintor_service.client;

import com.infnet.projeto_de_bloco.inspecaoExtintor_service.dto.ExtintorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "extintor-service")
public interface ExtintorClient {

    @GetMapping("/extintor/controleInterno/{numeroControleInterno}")
    ExtintorDTO getByNumeroControleInterno(@PathVariable int numeroControleInterno);
}