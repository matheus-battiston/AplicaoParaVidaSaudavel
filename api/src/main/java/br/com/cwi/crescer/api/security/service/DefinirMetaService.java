package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirMetaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.CalcularRecomendacoesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefinirMetaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Transactional
    public void definirMeta(DefinirMetaRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setMeta(request.getMeta());
        calcularRecomendacoesUsuarioService.calcular();

        usuarioRepository.save(usuario);

    }
}
