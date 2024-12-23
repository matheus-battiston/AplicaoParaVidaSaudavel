package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.EditarPrivacidadeRequest;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditarPrivacidadeService {

    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void editar(EditarPrivacidadeRequest request, Long id) {
        Post post = buscarPostService.porId(id);
        post.setPrivado(request.isNovaPrivacidade());

        postRepository.save(post);
    }
}
