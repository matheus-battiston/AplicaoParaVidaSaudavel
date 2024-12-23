package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ValidatorJaCurtiu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverCurtidaService {
    @Autowired
    private BuscarPostService buscarPostService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidatorJaCurtiu validatorJaCurtiu;


    @Transactional
    public void remover(Long idPost) {
        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porIdComAcesso(idPost, usuario);

        validatorJaCurtiu.naoCurtiu(post, usuario.getPostsCurtidos());

        usuario.removerCurtidaPost(post);

        usuarioRepository.save(usuario);
    }
}
