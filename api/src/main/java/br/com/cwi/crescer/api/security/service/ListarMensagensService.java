package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.repository.MensagemRepository;
import br.com.cwi.crescer.api.security.controller.response.MensagemResponse;
import br.com.cwi.crescer.api.security.mapper.MensagemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListarMensagensService {
    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    public List<MensagemResponse> listar(Long idConversa){
        Optional<Conversa> conversa = conversaRepository.findById(idConversa);

        if(conversa.isPresent()){
            List<Mensagem> mensagens = mensagemRepository.findAllByConversa(conversa.get());
            return mensagens.stream()
                .map(MensagemMapper::toResponse)
                .collect(Collectors.toList());
        } else return null;
    }

}
