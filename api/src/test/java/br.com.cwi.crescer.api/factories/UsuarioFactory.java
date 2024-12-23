package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.security.domain.Usuario;

import java.util.ArrayList;

public class UsuarioFactory {

    public static Usuario get() {
        Usuario usuario = new Usuario();
        usuario.setId(SimpleFactory.getRandomLong());
        usuario.setNome("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setRefeicoes(new ArrayList<>());
        usuario.setPontuacao("0");
        usuario.setConquistas(new ArrayList<>());

        return usuario;
    }
}
