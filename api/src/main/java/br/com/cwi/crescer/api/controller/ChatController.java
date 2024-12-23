package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.model.MensagemModel;
import br.com.cwi.crescer.api.service.PersistirConversaService;
import br.com.cwi.crescer.api.service.PersistirMensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private PersistirConversaService persistirConversaService;

    @Autowired
    private PersistirMensagemService persistirMensagemService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public MensagemModel receiveMessage(@Payload MensagemModel mensagemModel){
        return mensagemModel;
    }

    @MessageMapping("/private-message")
    public MensagemModel recMessage(@Payload MensagemModel mensagemModel){
        simpMessagingTemplate.convertAndSendToUser(mensagemModel.getReceiverId(),"/private", mensagemModel);
        persistirConversaService.persistir(mensagemModel);
        persistirMensagemService.persistir(mensagemModel);
        return mensagemModel;
    }
}
