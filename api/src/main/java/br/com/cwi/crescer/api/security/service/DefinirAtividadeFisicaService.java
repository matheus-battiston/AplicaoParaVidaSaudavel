package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirAtivdadeRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.CalcularRecomendacoesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefinirAtividadeFisicaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Transactional
    public void definir(DefinirAtivdadeRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setAtividadeFisica(request.getAtividadeFisica());

        calcularRecomendacoesUsuarioService.calcular();
        usuarioRepository.save(usuario);
    }
}
