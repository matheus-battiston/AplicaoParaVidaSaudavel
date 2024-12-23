package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.mapper.AlimentoMapper;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DetalharReceitaService {
    @Autowired
    private BuscarReceitaService buscarReceitaService;
    @Autowired
    private CalcularAlimentoService calcularAlimentoService;
    @Autowired
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;
    public ReceitaResponse detalhar(Long id) {
        Receita receita = buscarReceitaService.porId(id);
        boolean avaliado = receitaJaAvaliadaValidator.validar(receita);
        ReceitaResponse response = ReceitaMapper.toResponse(receita, avaliado);
        response.setAlimentos(
                receita.getAlimentos().stream()
                        .map(alimento -> AlimentoMapper.toResponse(calcularAlimentoService.calcularNutrientes(alimento)))
                        .collect(Collectors.toList())
        );
        return response;
    }
}
