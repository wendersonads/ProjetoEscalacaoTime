package br.com.duxusdesafio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.Time;

@Repository
public interface ApiRepositoryTime extends JpaRepository<Time,Long> {

     
    @Query(nativeQuery =true, value = "select * from time t where t.data = :data" )
    List<Time> TodosOstimesData (@Param("data") LocalDate data);
    
}
