package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarReceitaService {
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Receita porId(Long id) {
        Usuario logado = usuarioAutenticadoService.get();
        return receitaRepository.findAllowedById(id, logado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Receita não encontrada"));
    }
}
