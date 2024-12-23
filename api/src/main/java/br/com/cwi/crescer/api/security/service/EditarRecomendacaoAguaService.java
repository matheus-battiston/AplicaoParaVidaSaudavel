package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarRecomendacaoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.validator.EditarRecomendacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarRecomendacaoAguaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void editar(EditarRecomendacaoRequest request){
        editarRecomendacaoValidator.validar(request.getValor());

        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setAguaRecomendacao(request.getValor());
        usuarioRepository.save(usuario);
    }
}
