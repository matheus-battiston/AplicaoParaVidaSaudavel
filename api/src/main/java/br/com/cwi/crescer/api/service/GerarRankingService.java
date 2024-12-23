package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.PessoaRankingResponse;
import br.com.cwi.crescer.api.mapper.PessoaRankingMapper;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GerarRankingService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Page<PessoaRankingResponse> gerar(Pageable pageable) {
        Page<Usuario> usuariosRankeados = usuarioRepository.findRanking(pageable);

        return usuariosRankeados.map(PessoaRankingMapper::toResponse);
    }
}
