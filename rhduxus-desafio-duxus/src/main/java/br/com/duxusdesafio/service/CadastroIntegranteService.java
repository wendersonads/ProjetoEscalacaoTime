package br.com.duxusdesafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.DTO.IntegranteDTO;
import br.com.duxusdesafio.repository.CadastroIntegranteRepository;

@Service
public class CadastroIntegranteService {

    @Autowired
    private CadastroIntegranteRepository repository;

    public Integrante novoIntegrante(@RequestBody Integrante integrante) {

        List<ComposicaoTime> composicao = new ArrayList<>();
        Integrante novoIntegrante = new Integrante();

        if (integrante.getNome().isEmpty() || integrante.getFranquia().isEmpty() || integrante.getFuncao().isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Todos os dados devem ser preenchidos para realizar o cadastro!");

        } else {
            novoIntegrante.setComposicaoTime(composicao);
            novoIntegrante.setFranquia(integrante.getFranquia());
            novoIntegrante.setFuncao(integrante.getFuncao());
            novoIntegrante.setNome(integrante.getNome());

        }
        return repository.save(novoIntegrante);

    }

    public List<IntegranteDTO> todosIntegrantes() {

        List<Integrante> todosIntegrantes = new ArrayList<>();

        todosIntegrantes = repository.findAll();

        return todosIntegrantes.stream().map(this::convertDTO).collect(Collectors.toList());
      

    }

    private IntegranteDTO convertDTO(Integrante integrante) {
        IntegranteDTO dto = new IntegranteDTO();
        dto.setId(integrante.getId());
        dto.setNome(integrante.getNome());
        dto.setFuncao(integrante.getFuncao());
        dto.setFranquia(integrante.getFranquia());

        return dto;

    }
}
