package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceitaJaAvaliadaValidator {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    public boolean validar(Receita receita) {
        Usuario logado = usuarioAutenticadoService.get();
        return receita.getAvaliacoes().stream().anyMatch(avaliacaoReceita -> avaliacaoReceita.getUsuario().equals(logado));
    }
}
