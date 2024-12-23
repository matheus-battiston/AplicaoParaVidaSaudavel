package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.mapper.PesoMapper;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.security.controller.request.AdicionarPesoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.CalcularRecomendacoesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdicionarPesoService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private PesoRepository pesoRepository;
    @Autowired
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Transactional
    public void adicionar(AdicionarPesoRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        Peso novoPeso = PesoMapper.toEntity(request.getPeso().toString(), request.getData(), usuario);
        Peso pesoRecente = pesoRepository.findFirstByUsuarioOrderByDataRegistroDesc(usuario);
        usuario.adicionarPeso(novoPeso);

        if (novoPeso.getDataRegistro().isAfter(pesoRecente.getDataRegistro()) || novoPeso.getDataRegistro().isEqual(pesoRecente.getDataRegistro())){
            usuario.alterarPesoAtual(novoPeso);
            usuario.calcularIMC();
        }

        pesoRepository.save(novoPeso);
        calcularRecomendacoesUsuarioService.calcular();
    }
}
