package br.com.duxusdesafio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private  ApiService service;

    @GetMapping("/{data}")
    public List<String> nomesDosIntegrantesPorData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        return service.nomesDosIntegrantesPorData(data,null);
    }
}
