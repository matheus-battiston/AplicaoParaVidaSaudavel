package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Categoria;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.TipoConquista;

public class ConquistaFactory {
    public static Conquista get(){
        Conquista conquista = new Conquista();
        conquista.setCategoria(Categoria.BRONZE);
        conquista.setId(SimpleFactory.getRandomLong());
        conquista.setDescricao("Descricao da conquista");
        conquista.setTipo(TipoConquista.AGUA);
        conquista.setValor(50L);
        return conquista;
    }
}
