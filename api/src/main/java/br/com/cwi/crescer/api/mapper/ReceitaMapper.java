package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReceitaMapper {
    public static Receita toEntity(ReceitaRequest request) {
        return Receita.builder()
                .modoPreparo(request.getModoPreparo())
                .imagem(request.getImagem())
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .privado(request.getPrivado())
                .copia(request.getCopia())
                .dataCriacao(LocalDate.now())
                .alimentos(new ArrayList<>())
                .build();
    }

    public static ReceitaResponse toResponse(Receita receita, boolean avaliado) {
        return ReceitaResponse.builder()
                .id(receita.getId())
                .avaliado(avaliado)
                .imagem(receita.getImagem())
                .modoPreparo(receita.getModoPreparo())
                .titulo(receita.getTitulo())
                .descricao(receita.getDescricao())
                .dataCriacao(receita.getDataCriacao())
                .privado(receita.isPrivado())
                .copia(receita.isCopia())
                .calorias(receita.getCalorias())
                .proteinas(receita.getProteinas())
                .carboidratos(receita.getCarboidratos())
                .lipidios(receita.getLipidios())
                .usuarioId(receita.getUsuario().getId())
                .criadorNome(receita.getCriador().getNome())
                .criadorId(receita.getCriador().getId())
                .nota(receita.getNota())
                .build();
    }

    public static Receita copiar(Receita original) {
        return Receita.builder()
                .id(null)
                .avaliacoes(new ArrayList<>())
                .copia(true)
                .privado(true)
                .titulo(original.getTitulo())
                .proteinas(original.getProteinas())
                .calorias(original.getCalorias())
                .carboidratos(original.getCarboidratos())
                .lipidios(original.getLipidios())
                .descricao(original.getDescricao())
                .imagem(original.getImagem())
                .modoPreparo(original.getModoPreparo())
                .nota("0")
                .criador(original.getCriador())
                .alimentos(new ArrayList<>())
                .build();
    }
}
