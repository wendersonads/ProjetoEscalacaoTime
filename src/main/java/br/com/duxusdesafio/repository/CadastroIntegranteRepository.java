package br.com.duxusdesafio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.Integrante;
import java.util.List;

@Repository
public interface CadastroIntegranteRepository extends JpaRepository<Integrante, Long> {

    Optional<Integrante> findByNome(String nome);

    @Query(nativeQuery = true, value = "select * from integrante")
    List<Integrante> listarTodos();
}
