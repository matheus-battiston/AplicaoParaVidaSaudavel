package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.security.mapper.ConversaMapper.toEntity;

@Service
public class AdicionarConversaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private ValidaConversaJaExisteService validaConversaJaExisteService;

    public void adicionar(Long idUsuario){
        Usuario usuario = usuarioAutenticadoService.get();
        Usuario usuarioAAdicionar = buscarUsuarioService.porId(idUsuario);

        validaConversaJaExisteService.validar(usuario, usuarioAAdicionar);

        Conversa entity = toEntity(usuario, usuarioAAdicionar);
        conversaRepository.save(entity);
    }

}
