package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.PesoResponse;
import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.security.domain.Usuario;

import java.time.LocalDate;

public class PesoMapper {
    public static Peso toEntity(String peso, LocalDate hoje, Usuario usuario) {
        return Peso.builder()
                .dataRegistro(hoje)
                .valor(peso)
                .usuario(usuario)
                .build();
    }

    public static PesoResponse toResponse(Peso peso) {
        return PesoResponse.builder()
                .peso(peso.getValor())
                .data(peso.getDataRegistro())
                .build();
    }
}
