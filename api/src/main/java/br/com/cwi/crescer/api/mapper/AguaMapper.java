package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.domain.Agua;

public class AguaMapper {
    public static Agua toEntity(AguaRequest request){
        return Agua.builder()
                .quantidade(request.getQuantidade())
                .dataRegistro(request.getData())
                .build();
    }
}
