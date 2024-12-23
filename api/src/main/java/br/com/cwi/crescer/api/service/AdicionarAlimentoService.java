package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AlimentoRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.domain.TipoConquista.ALIMENTO;
import static br.com.cwi.crescer.api.mapper.AlimentoMapper.toEntity;

@Service
public class AdicionarAlimentoService {
    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ConquistasService conquistasService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public void adicionar(AlimentoRequest request){
        Usuario usuario = usuarioAutenticadoService.get();
        Alimento alimento = toEntity(request);
        alimento.setComunidade(true);
        conquistasService.conquistasQuantidadeDeAcoes(usuario, ALIMENTO);

        alimentoRepository.save(alimento);
    }
}
