package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.PostResponse;
import br.com.cwi.crescer.api.domain.Post;

public class PostResponseMapper {
    public static PostResponse toResponse(Post post, boolean jaCurtiu) {
        return PostResponse.builder()
                .id(post.getId())
                .texto(post.getTexto())
                .imagem(post.getImagem())
                .nroComentarios(post.getComentariosPost().size())
                .nroCurtidas(post.getUsuariosCurtiram().size())
                .autor(ListarPessoaMapper.toResponse(post.getUsuario()))
                .dataInclusao(post.getDataInclusao().toLocalDate())
                .curtido(jaCurtiu)
                .privado(post.isPrivado())
                .build();
    }
}
