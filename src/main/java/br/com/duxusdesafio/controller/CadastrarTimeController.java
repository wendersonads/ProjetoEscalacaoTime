package br.com.duxusdesafio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.CadastroTimeService;

@RestController
@RequestMapping("/time")
public class CadastrarTimeController {

    @Autowired
    public CadastroTimeService service;

    @PostMapping("")
    public String novoTime(@RequestBody List<Long> idsIntegrantes) {
        return service.novoTime(idsIntegrantes);
    }

}
