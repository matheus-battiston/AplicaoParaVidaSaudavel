package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.validator.ValidatorJaSegue;
import br.com.cwi.crescer.api.service.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnfollowUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidatorJaSegue validatorJaSegue;

    @Transactional
    public void unfollow(Long idUsuario) {
        Usuario usuarioAutenticado = usuarioAutenticadoService.get();
        Usuario seguido = buscarUsuarioService.porId(idUsuario);

        validatorJaSegue.validarJaSegue(usuarioAutenticado, seguido);
        usuarioAutenticado.deixarDeSeguir(seguido);

        usuarioRepository.save(usuarioAutenticado);

    }
}
