package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.mapper.AlimentoMapper;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ListarAlimentosService {
    @Autowired
    private AlimentoRepository alimentoRepository;

    public Page<AlimentoResponse> listarPaginado(String paramValue, Pageable pageable) {
        return alimentoRepository.findByNomeContainsIgnoreCase(paramValue, pageable).map(AlimentoMapper::toResponse);
    }
}
