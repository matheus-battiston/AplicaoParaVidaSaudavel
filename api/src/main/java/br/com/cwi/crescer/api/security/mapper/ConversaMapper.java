package br.com.cwi.crescer.api.security.mapper;

import br.com.cwi.crescer.api.controller.model.Status;
import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.security.controller.response.ConversaResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class ConversaMapper {
    private String senderId;
    private String receiverId;
    private String message;
    private String date;
    private Status status;

    public static Conversa toEntity(Usuario usuario1, Usuario usuario2){
        return Conversa.builder()
                .primeiroUsuario(usuario1)
                .segundoUsuario(usuario2)
                .build();
    }

    public static ConversaResponse toResponse(Usuario usuario, Conversa conversa){
        return ConversaResponse.builder()
                .idConversa(conversa.getId())
                .idUsuario(usuario.getId())
                .nome(usuario.getNome())
                .foto(usuario.getImagemPerfil())
                .build();
    }

}
