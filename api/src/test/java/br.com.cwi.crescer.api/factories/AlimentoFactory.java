package br.com.cwi.crescer.api.factories;


import br.com.cwi.crescer.api.controller.request.AlimentoRequest;
import br.com.cwi.crescer.api.domain.Alimento;

public class AlimentoFactory {

    public static Alimento getBuilder() {
        return Alimento.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("alimento teste")
                .lipidios("100")
                .proteinas("100")
                .calorias("100")
                .carboidratos("100")
                .build();
    }


    public static AlimentoRequest getRequest() {
        AlimentoRequest request = new AlimentoRequest();
        request.setNome("alimento teste");
        request.setLipidios("123");
        request.setProteinas("1");
        request.setCalorias("123");
        request.setCarboidratos("123");
        return request;
    }
}
