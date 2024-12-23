package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AvaliacaoReceitaRequest;
import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.mapper.AvaliacaoReceitaMapper;
import br.com.cwi.crescer.api.repository.AvaliacaoReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.domain.TipoConquista.AVALIAR_RECEITAS;

@Service
public class AvaliacaoReceitaService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private BuscarReceitaService buscarReceitaService;
    @Autowired
    private AvaliacaoReceitaRepository avaliacaoReceitaRepository;
    @Autowired
    private ConquistasService conquistasService;

    @Transactional
    public void avaliar(Long receitaId, AvaliacaoReceitaRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        Receita receita = buscarReceitaService.porId(receitaId);
        int nota = Integer.parseInt(request.getNota());
        double notaAtual = Double.parseDouble(receita.getNota());

        double novaNota = (
                (notaAtual * receita.getAvaliacoes().size()) + nota)
                / (receita.getAvaliacoes().size()+1);

        receita.setNota(String.format("%.1f", novaNota).replace(',', '.'));

        AvaliacaoReceita avaliacaoReceita = AvaliacaoReceitaMapper.toEntity(usuario,nota);
        receita.adicionarNota(avaliacaoReceita);

        conquistasService.conquistasQuantidadeDeAcoes(usuario, AVALIAR_RECEITAS);

        avaliacaoReceitaRepository.save(avaliacaoReceita);
    }
}
