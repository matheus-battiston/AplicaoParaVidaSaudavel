package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.model.MensagemModel;
import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.security.mapper.ConversaMapper.toEntity;

@Service
public class PersistirConversaService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private ConversaRepository conversaRepository;

    public void persistir(MensagemModel mensagem){
        Usuario remetente = buscarUsuarioService.porId(Long.valueOf(mensagem.getSenderId()));
        Usuario destinatario = buscarUsuarioService.porId(Long.valueOf(mensagem.getReceiverId()));
        Conversa conversa = toEntity(remetente, destinatario);
        boolean existe = conversaRepository.existsByPrimeiroUsuarioAndSegundoUsuario(remetente, destinatario);
        boolean existeUsuariosTrocados = conversaRepository.existsByPrimeiroUsuarioAndSegundoUsuario(destinatario, remetente);

        if(!existe && !existeUsuariosTrocados
                && !remetente.equals(destinatario)){
            conversaRepository.save(conversa);
        }
    }
}
