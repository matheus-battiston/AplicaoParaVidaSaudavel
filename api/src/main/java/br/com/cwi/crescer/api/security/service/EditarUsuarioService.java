package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarAlturaRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarDataNascimentoRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarEmailRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarNomeRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.CalcularRecomendacoesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditarUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Transactional
    public void nome(EditarNomeRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setNome(request.getNome());

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void data(EditarDataNascimentoRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setDataNascimento(request.getData());

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void email(EditarEmailRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setEmail(request.getEmail());

        usuarioRepository.save(usuario);
    }

    public void altura(EditarAlturaRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setAltura(request.getAltura());


        usuarioRepository.save(usuario);
        calcularRecomendacoesUsuarioService.calcular();
    }
}
