package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarRecomendacaoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.validator.EditarRecomendacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarRecomendacaoCaloriasService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    public void editar(EditarRecomendacaoRequest request){
        editarRecomendacaoValidator.validar(request.getValor());
        Usuario usuario = usuarioAutenticadoService.get();

        usuario.setCaloriasRecomendacao(request.getValor());

        usuarioRepository.save(usuario);
    }
}
