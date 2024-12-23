package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.mapper.PesoMapper;
import br.com.cwi.crescer.api.mapper.UsuarioMapper;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.InformacoesIniciaisRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.validator.ValidatorJaTemPesoInicial;
import br.com.cwi.crescer.api.service.CalcularRecomendacoesUsuarioService;
import br.com.cwi.crescer.api.service.NowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DefinirInformacoesIniciaisService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ValidatorJaTemPesoInicial validatorJaTemPesoInicial;
    @Autowired
    private NowService nowService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

   @Transactional
    public UsuarioResponse definir(InformacoesIniciaisRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();

        usuario.setAltura(request.getAltura());
        usuario.setMeta(request.getMeta());
        usuario.setSexo(request.getSexo());
        usuario.setAtividadeFisica(request.getAtividadeFisica());
        usuario.setImagemPerfil(request.getImagem());

        validatorJaTemPesoInicial.confirmaQueNaoTem(usuario);
        String peso = request.getPeso();
        LocalDate hoje = nowService.nowDate();

        Peso novoPeso = PesoMapper.toEntity(peso, hoje, usuario);
        usuario.adicionarPeso(novoPeso);
        usuario.alterarPesoAtual(novoPeso);

        usuarioRepository.save(usuario);
        calcularRecomendacoesUsuarioService.calcular();

       return UsuarioMapper.toResponse(usuario);

    }
}
