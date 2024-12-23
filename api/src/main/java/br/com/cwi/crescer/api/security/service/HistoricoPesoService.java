package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.controller.response.PesoResponse;
import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.mapper.PesoMapper;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoPesoService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private PesoRepository pesoRepository;
    public List<PesoResponse> gerar() {
        Usuario usuario = usuarioAutenticadoService.get();
        List<Peso> pesos = pesoRepository.findAllByUsuarioOrderByDataRegistroDesc(usuario);

        return pesos.stream()
                .map(PesoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
