package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.NovoComentarioRequest;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.ComentarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.COMENTAR_POST;
import static br.com.cwi.crescer.api.mapper.ComentarioPostMapper.toEntity;

@Service
public class PostarNovoComentarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private BuscarPostService buscarPostService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ConquistasService conquistasService;

    @Transactional
    public void postar(NovoComentarioRequest request, Long id) {
        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porIdComAcesso(id, usuario);

        ComentarioPost novoComentario = toEntity(request.getTexto(), usuario, post);

        post.adicionarComentario(novoComentario);
        conquistasService.conquistasQuantidadeDeAcoes(usuario, COMENTAR_POST);


        comentarioRepository.save(novoComentario);
    }
}
