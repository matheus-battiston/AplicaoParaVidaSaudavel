package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaPreferenciasRequest;
import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.mapper.AlimentoMapper.toResponse;
import static java.util.stream.Collectors.toList;

@Service
public class ExibirPrincipaisReceitasService {
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private ReceitaAlimentoRepository receitaAlimentoRepository;
    @Autowired
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    public Page<ReceitaResponse> exibir(Pageable pageable, ReceitaPreferenciasRequest request) {

        List<ReceitaAlimento> alergias =  request.getAlergias().stream()
                .map(alergia -> receitaAlimentoRepository.findByAlimentoNome(alergia))
                .reduce(new ArrayList<>(), (acc, cur) -> {
                    acc.addAll(cur);
                    return acc;
                });

        Page<Receita> receitas = receitaRepository.findTopPorAlergias(alergias, pageable);
        return receitas.map(receita -> {
            boolean avaliado = receitaJaAvaliadaValidator.validar(receita);
            ReceitaResponse response = ReceitaMapper.toResponse(receita,avaliado);
            response.setAlimentos(
                    receita.getAlimentos().stream()
                            .map(alimento -> {
                                AlimentoResponse responseAlimento = toResponse(alimento.getAlimento());
                                responseAlimento.setQuantidade(alimento.getQuantidade());
                                return responseAlimento;
                            })
                            .collect(toList())
            );
            return response;
        });
    }

}
