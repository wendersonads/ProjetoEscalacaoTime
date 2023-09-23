package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.CadastroComposicaoRepository;
import br.com.duxusdesafio.repository.CadastroTimeRepository;

@Service
public class CadastroTimeService {

    @Autowired
    private CadastroTimeRepository repository;

    @Autowired
    private CadastroComposicaoRepository composicaoRepository;

    public Time novoTime(@RequestBody Map<String, List<Integrante>> requestBody) {

        List<Integrante> integrantes = requestBody.get("integrantes");

        if (integrantes == null || integrantes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A lista de integrantes não pode estar vazia.");
        }

        LocalDate dataDoDia = LocalDate.now();
        List<ComposicaoTime> composicoes = new ArrayList<>();
        Time novoTime = new Time(dataDoDia, composicoes);

        try {
            novoTime = repository.save(novoTime);

            for (Integrante integrante : integrantes) {
                composicoes.add(new ComposicaoTime(novoTime, integrante));
            }

            composicaoRepository.saveAll(composicoes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar novo time: " + e.getMessage());
        }

        return novoTime;
    }
}
