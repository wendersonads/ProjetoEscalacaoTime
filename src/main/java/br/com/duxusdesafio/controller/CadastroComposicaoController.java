package br.com.duxusdesafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.service.CadastroComposicaoService;

@RestController
@RequestMapping("/composicao")
public class CadastroComposicaoController {

    @Autowired
    private CadastroComposicaoService service;

    @PostMapping("")
    public ComposicaoTime novaComposicao(@RequestBody ComposicaoTime compTime) {

        return service.novaComposicao(compTime);
    }

}
