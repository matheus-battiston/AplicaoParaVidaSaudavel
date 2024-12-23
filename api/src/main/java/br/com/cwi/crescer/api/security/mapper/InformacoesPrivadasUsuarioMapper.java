package br.com.cwi.crescer.api.security.mapper;

import br.com.cwi.crescer.api.security.controller.response.InformacoesPrivadasUsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class InformacoesPrivadasUsuarioMapper {
    public static InformacoesPrivadasUsuarioResponse toResponse(Usuario usuario) {
        return InformacoesPrivadasUsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .altura(usuario.getAltura())
                .peso(usuario.getPeso())
                .imagemPerfil(usuario.getImagemPerfil())
                .imc(usuario.getImc())
                .gastoCalorico(usuario.getGastoCalorico())
                .aguaRecomendacao(usuario.getAguaRecomendacao())
                .proteinasRecomendacao(usuario.getProteinasRecomendacao())
                .caloriasRecomendacao(usuario.getCaloriasRecomendacao())
                .carboidratosRecomendacao(usuario.getCarboidratosRecomendacao())
                .lipidiosRecomendacao(usuario.getLipidiosRecomendacao())
                .meta(usuario.getMeta())
                .atividadeFisica(usuario.getAtividadeFisica())
                .nroSeguidores(usuario.getSeguidores().size())
                .pontuacao(usuario.getPontuacao())
                .categoriaTag(usuario.getCategoriaTitulo())
                .build();
    }
}
