package br.com.duxusdesafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.DTO.IntegranteDTO;
import br.com.duxusdesafio.service.CadastroIntegranteService;

@RestController
@RequestMapping("/integrante")
public class CadastroIntegranteController {

    @Autowired
    private CadastroIntegranteService service;

    @PostMapping("")
    public Integrante novoIntegrante(@RequestBody Integrante integrante) {

        return service.novoIntegrante(integrante);
    }

    @GetMapping("/")
    public List<IntegranteDTO> todosIntegrantes() {
        return service.todosIntegrantes();

    }
}
