package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.AlimentoMapper.toResponse;
import static java.util.stream.Collectors.toList;

@Service
public class ListarReceitasDoUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;
    @Autowired
    private ReceitaRepository receitaRepository;

    public Page<ReceitaResponse> listar(Long usuarioId, Pageable pageable) {
        Usuario logado = usuarioAutenticadoService.get();
        Page<Receita> receitas = receitaRepository.findAllowedByUsuarioId(usuarioId, logado, pageable);

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
