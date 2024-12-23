package br.com.cwi.crescer.api.controller.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MensagemModel {
    private String senderId;
    private String receiverId;
    private String message;
    private String date;
    private Status status;
}
