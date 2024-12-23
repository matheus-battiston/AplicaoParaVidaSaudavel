package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.NovoPostRequest;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.mapper.NovoPostMapper;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.POSTS;

@Service
public class PostarService {
    private static final Long PONTOS = 10L;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NowService nowService;

    @Autowired
    private ConquistasService conquistasService;

    @Transactional
    public void postar(NovoPostRequest request) {
        Usuario usuarioLogado = usuarioAutenticadoService.get();
        Post novoPost = NovoPostMapper.toEntity(request);

        novoPost.setDataInclusao(nowService.now());
        novoPost.setUsuario(usuarioLogado);

        usuarioLogado.getPosts().add(novoPost);
        usuarioLogado.adicionarPontos(PONTOS);
        conquistasService.conquistasQuantidadeDeAcoes(usuarioLogado, POSTS);
        conquistasService.pontos(usuarioLogado);

        postRepository.save(novoPost);
    }
}
