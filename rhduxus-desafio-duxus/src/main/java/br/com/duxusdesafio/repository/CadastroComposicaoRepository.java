package br.com.duxusdesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.ComposicaoTime;

@Repository
public interface CadastroComposicaoRepository extends JpaRepository<ComposicaoTime,Long> {
    
}
