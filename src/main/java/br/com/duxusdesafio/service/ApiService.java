package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.ApiRepositoryTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * @author carlosau
 */
@Service
public class ApiService {

    @Autowired
    private ApiRepositoryTime repositoryTime;

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time daquela data
     */
    public List<String> nomesDosIntegrantesPorData(LocalDate data, List<Time> todosOsTimes) {
        List<String> nomesDosIntegrantes = new ArrayList<>();
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {

            todosOsTimes = repositoryTime.findAll();
        }

        try {
            for (Time time : todosOsTimes) {
                LocalDate dataDoTime = time.getData();

                if (dataDoTime.isEqual(data)) {
                    for (ComposicaoTime composicao : time.getComposicaoTime()) {
                        nomesDosIntegrantes.add(composicao.getIntegrante().getNome());
                    }
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Buscar Integrantes! " + e.getMessage());
        }

        return nomesDosIntegrantes;
    }

    /**
     * Vai retornar o integrante que tiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        Map<Long, Integer> contagemIntegrantes = new HashMap<>();
        Integrante integranteMaisUsado = null;
        int contagemMaxima = 0;
        try {

            for (Time time : todosOsTimes) {
                LocalDate dataDoTime = time.getData();

                if (dataDoTime.isEqual(dataInicial)
                        || (dataDoTime.isAfter(dataInicial) && dataDoTime.isBefore(dataFinal))) {
                    for (ComposicaoTime composicao : time.getComposicaoTime()) {
                        Integrante integrante = composicao.getIntegrante();
                        long integranteId = integrante.getId();

                        if (!contagemIntegrantes.containsKey(integranteId)) {
                            contagemIntegrantes.put(integranteId, 1);
                        } else {
                            contagemIntegrantes.put(integranteId, contagemIntegrantes.get(integranteId) + 1);
                        }

                        if (contagemIntegrantes.get(integranteId) > contagemMaxima) {
                            contagemMaxima = contagemIntegrantes.get(integranteId);
                            integranteMaisUsado = integrante;
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Buscar Integrante! " + e.getMessage());
        }

        return integranteMaisUsado;
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período
     */
    public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        Map<String, Integer> contagemNomesDeTimes = new HashMap<>();
        String nomeDoTimeMaisComum = null;
        int contagemMaxima = 0;
        List<String> nomesDosIntegrantesDoTimeMaisComum = new ArrayList<>();

        try {
            for (Time time : todosOsTimes) {
                LocalDate dataDoTime = time.getData();

                if (dataDoTime.isEqual(dataInicial)
                        || (dataDoTime.isAfter(dataInicial) && dataDoTime.isBefore(dataFinal))) {
                    for (ComposicaoTime composicao : time.getComposicaoTime()) {
                        String nomeDoTime = composicao.getIntegrante().getFranquia();

                        if (!contagemNomesDeTimes.containsKey(nomeDoTime)) {
                            contagemNomesDeTimes.put(nomeDoTime, 1);
                        } else {
                            contagemNomesDeTimes.put(nomeDoTime, contagemNomesDeTimes.get(nomeDoTime) + 1);
                        }

                        if (contagemNomesDeTimes.get(nomeDoTime) > contagemMaxima) {
                            contagemMaxima = contagemNomesDeTimes.get(nomeDoTime);
                            nomeDoTimeMaisComum = nomeDoTime;
                        }
                    }
                }
            }

            for (Time time : todosOsTimes) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    if (composicao.getIntegrante().getFranquia().equals(nomeDoTimeMaisComum)) {
                        nomesDosIntegrantesDoTimeMaisComum.add(composicao.getIntegrante().getNome());
                    }
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Buscar Time! " + e.getMessage());
        }

        return nomesDosIntegrantesDoTimeMaisComum;
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        Map<String, Integer> contagemFuncoes = new HashMap<>();
        String funcaoMaisComum = null;
        int contagemMaxima = 0;
        try {

            for (Time time : todosOsTimes) {
                LocalDate dataDoTime = time.getData();

                if (dataDoTime.isEqual(dataInicial)
                        || (dataDoTime.isAfter(dataInicial) && dataDoTime.isBefore(dataFinal))) {
                    for (ComposicaoTime composicao : time.getComposicaoTime()) {
                        String funcaoDoIntegrante = composicao.getIntegrante().getFuncao();

                        if (!contagemFuncoes.containsKey(funcaoDoIntegrante)) {
                            contagemFuncoes.put(funcaoDoIntegrante, 1);
                        } else {
                            contagemFuncoes.put(funcaoDoIntegrante, contagemFuncoes.get(funcaoDoIntegrante) + 1);
                        }

                        if (contagemFuncoes.get(funcaoDoIntegrante) > contagemMaxima) {
                            contagemMaxima = contagemFuncoes.get(funcaoDoIntegrante);
                            funcaoMaisComum = funcaoDoIntegrante;
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Buscar Função! " + e.getMessage());
        }

        return funcaoMaisComum;
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        Map<String, Integer> contagemFranquias = new HashMap<>();
        String franquiaMaisFamosa = null;
        int contagemMaxima = 0;
        try {

            for (Time time : todosOsTimes) {
                LocalDate dataDoTime = time.getData();

                if (dataDoTime.isEqual(dataInicial)
                        || (dataDoTime.isAfter(dataInicial) && dataDoTime.isBefore(dataFinal))) {
                    for (ComposicaoTime composicao : time.getComposicaoTime()) {
                        String franquiaDoIntegrante = composicao.getIntegrante().getFranquia();

                        if (!contagemFranquias.containsKey(franquiaDoIntegrante)) {
                            contagemFranquias.put(franquiaDoIntegrante, 1);
                        } else {
                            contagemFranquias.put(franquiaDoIntegrante,
                                    contagemFranquias.get(franquiaDoIntegrante) + 1);
                        }

                        if (contagemFranquias.get(franquiaDoIntegrante) > contagemMaxima) {
                            contagemMaxima = contagemFranquias.get(franquiaDoIntegrante);
                            franquiaMaisFamosa = franquiaDoIntegrante;
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Erro ao Buscar Franquia mais famosa! " + e.getMessage());
        }

        return franquiaMaisFamosa;
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        List<Time> timesNoPeriodo = todosOsTimes.stream()
                .filter(time -> isDataNoPeriodo(time.getData(), dataInicial, dataFinal))
                .collect(Collectors.toList());

        Map<String, Long> contagemPorFranquia = new HashMap<>();

        try {

            for (Time time : timesNoPeriodo) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    String franquia = composicao.getIntegrante().getFranquia();

                    contagemPorFranquia.put(franquia, contagemPorFranquia.getOrDefault(franquia, 0L) + 1);
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Erro ao fazer contagem por franquia! " + e.getMessage());
        }

        return contagemPorFranquia;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        List<Time> timesNoPeriodo = todosOsTimes.stream()
                .filter(time -> isDataNoPeriodo(time.getData(), dataInicial, dataFinal))
                .collect(Collectors.toList());

        Map<String, Long> contagemPorFuncao = new HashMap<>();
        try {
            for (Time time : timesNoPeriodo) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    String funcao = composicao.getIntegrante().getFuncao();

                    contagemPorFuncao.put(funcao, contagemPorFuncao.getOrDefault(funcao, 0L) + 1);
                }
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Erro ao fazer contagem por função! " + e.getMessage());
        }

        return contagemPorFuncao;
    }

    public Time timeDaData(LocalDate data, List<Time> todosOsTimes) {

        /*
         * Caso a lista esteja vazia pegar dados do banco, estava dando erro de
         * NullException
         * na variável repositoryTime na chamada do método
         * na classe de TesteApiService
         */
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            todosOsTimes = repositoryTime.findAll();
        }
        for (Time time : todosOsTimes) {
            if (time.getData().isEqual(data)) {
                return time;
            }
        }
        return null;
    }

    // Função auxiliar para verificar se uma data está dentro de um período
    private boolean isDataNoPeriodo(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
        return (data.isEqual(dataInicial) || data.isAfter(dataInicial)) && data.isBefore(dataFinal);
    }

}
