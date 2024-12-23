package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.mapper.AlimentoMapper;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarAlimentosRecentementeUsadosService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    public List<AlimentoResponse> listar(){
        Usuario usuario = usuarioAutenticadoService.get();
        List<RefeicaoAlimento> recentementeUsados = refeicaoAlimentoRepository.findAllRecentlyUsed(usuario);

        return recentementeUsados.stream()
                .filter(receitaAlimento -> recentementeUsados.indexOf(receitaAlimento) < 5)
                .map(receitaAlimento -> AlimentoMapper.toResponse(receitaAlimento.getAlimento()))
                .collect(Collectors.toList());
    }

}
