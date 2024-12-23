package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ValidatorJaCurtiu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.CURTIR_POST;

@Service
public class CurtirPostService {
    @Autowired
    private BuscarPostService buscarPostService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ValidatorJaCurtiu validatorJaCurtiu;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConquistasService conquistasService;

    @Transactional
    public void curtir(Long idPost) {
        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porIdComAcesso(idPost, usuario);

        validatorJaCurtiu.jaCurtiu(post, usuario.getPostsCurtidos());
        usuario.adicionarCurtida(post);
        conquistasService.conquistasQuantidadeDeAcoes(usuario, CURTIR_POST);

        usuarioRepository.save(usuario);
    }
}
