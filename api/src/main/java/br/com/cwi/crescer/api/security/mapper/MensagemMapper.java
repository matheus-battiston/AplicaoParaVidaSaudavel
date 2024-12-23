package br.com.cwi.crescer.api.security.mapper;

import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.security.controller.response.MensagemResponse;

public class MensagemMapper {
    public static MensagemResponse toResponse(Mensagem entity){
        return MensagemResponse.builder()
                .idRemetente(entity.getRemetente().getId())
                .mensagem(entity.getTexto())
                .build();
    }
}
