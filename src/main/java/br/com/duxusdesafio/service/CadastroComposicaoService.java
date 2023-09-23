package br.com.duxusdesafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.repository.CadastroComposicaoRepository;

@Service
public class CadastroComposicaoService {

    @Autowired
    private CadastroComposicaoRepository repository;


    public ComposicaoTime novaComposicao(@RequestBody ComposicaoTime compTime){

        return repository.save(compTime);
    }

}
