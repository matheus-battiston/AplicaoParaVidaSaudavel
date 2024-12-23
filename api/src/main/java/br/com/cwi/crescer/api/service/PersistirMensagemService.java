package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.model.MensagemModel;
import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.repository.MensagemRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class PersistirMensagemService {
    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    public static final String MENSAGEM_CONVERSA_NAO_EXISTE = "Não existe uma conversa criada entre esses dois usuários";

    public void persistir(MensagemModel mensagem){
        Long idRemetente = valueOf(mensagem.getSenderId());
        Long idDestinatario = valueOf(mensagem.getReceiverId());
        Usuario remetente = buscarUsuarioService.porId(idRemetente);
        Usuario destinatario = buscarUsuarioService.porId(idDestinatario);
        List<Conversa> conversasRemetente = conversaRepository.findAllByPrimeiroUsuarioOrSegundoUsuario(remetente, remetente);
        Optional<Conversa> conversa = conversasRemetente.stream()
                .filter(conversaRemetente -> conversaRemetente.getPrimeiroUsuario().equals(destinatario)
                || conversaRemetente.getSegundoUsuario().equals(destinatario))
                .findFirst();

        if(conversa.isPresent()){
            Mensagem mensagemEntity = new Mensagem();
            mensagemEntity.setRemetente(remetente);
            mensagemEntity.setTexto(mensagem.getMessage());
            mensagemEntity.setConversa(conversa.get());
            mensagemRepository.save(mensagemEntity);
        } else throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_CONVERSA_NAO_EXISTE);

    }
}
