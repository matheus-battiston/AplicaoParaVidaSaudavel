package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarAlimentoService {
    public static final String MENSAGEM_ALIMENTO_NAO_ENCONTRADO = "Alimento não encontrado";
    @Autowired
    private AlimentoRepository alimentoRepository;
    public Alimento porId(Long id) {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENSAGEM_ALIMENTO_NAO_ENCONTRADO));
    }
}
