package br.com.cwi.crescer.api.security.service;


import br.com.cwi.crescer.api.controller.request.UsuarioRequest;
import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.mapper.ConquistaMapper;
import br.com.cwi.crescer.api.repository.ConquistaRepository;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.cwi.crescer.api.mapper.UsuarioMapper.toResponse;
import static br.com.cwi.crescer.api.security.domain.AtividadeFisica.P;
import static br.com.cwi.crescer.api.security.domain.Meta.M;


@Service
public class IncluirUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidarEmailUnicoService validarEmailUnicoService;
    @Autowired
    private PesoRepository pesoRepository;

    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Transactional
    public UsuarioResponse incluir(UsuarioRequest request) {
        List<Conquista> conquistas = conquistaRepository.findAll();
        validarEmailUnicoService.validar(request.getEmail());
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setSenha(getSenhaCriptografada(request.getSenha()));
        usuario.setEmail(request.getEmail());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setAtivo(true);
        usuario.setMeta(M);
        usuario.setAtividadeFisica(P);
        usuario.setPontuacao("0");

        conquistas.forEach(conquista -> usuario.getConquistas().add(ConquistaMapper.toConquistaUsuario(conquista, usuario)));

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }
}
