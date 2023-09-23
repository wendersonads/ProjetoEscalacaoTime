package br.com.duxusdesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.Time;

@Repository
public interface CadastroTimeRepository extends JpaRepository<Time,Long> {

    
    
}
