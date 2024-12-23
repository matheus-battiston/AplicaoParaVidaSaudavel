package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.mapper.AlimentoMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toResponse;

@Service
public class ListarRefeicoesService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;


    public List<RefeicaoResponse> listar(LocalDate dia){
        Usuario usuario = usuarioAutenticadoService.get();
        return usuario.getRefeicoes().stream()
                .filter(refeicao -> refeicao.getDia().isEqual(dia))
                .map(refeicao -> {
                    RefeicaoResponse response = toResponse(refeicao);
                    response.setAlimentos(refeicao.getAlimentos().stream()
                            .map(alimento -> {
                                AlimentoResponse responseAlimento = AlimentoMapper.toResponse(alimento.getAlimento());
                                responseAlimento.setQuantidade(alimento.getQuantidade());
                                return responseAlimento;
                            })
                            .collect(Collectors.toList()));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
