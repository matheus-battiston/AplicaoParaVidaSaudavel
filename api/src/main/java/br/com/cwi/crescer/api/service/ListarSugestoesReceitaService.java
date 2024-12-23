package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarSugestoesReceitaService {
    @Autowired
    private BuscarReceitaService buscarReceitaService;
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    public List<ReceitaResponse> listar(Long id) {
        Receita receita = buscarReceitaService.porId(id);

        return receitaRepository.findSugestions(receita.getCalorias().doubleValue(), receita.getProteinas().doubleValue(), receita.getId())
                .stream().map(receita1 -> {
                    boolean avaliado = receitaJaAvaliadaValidator.validar(receita1);
                    return ReceitaMapper.toResponse(receita1,avaliado);
                })
                .collect(Collectors.toList());
    }
}
