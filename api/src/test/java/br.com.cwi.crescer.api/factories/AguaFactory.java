package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.domain.Agua;

import java.time.LocalDate;

public class AguaFactory {
    public static Agua getBuilder(){
        return Agua.builder()
                .quantidade(SimpleFactory.getRandomLong().intValue())
                .build();
    }
    public static AguaRequest getRequest(){
        AguaRequest request = new AguaRequest();
        request.setQuantidade(SimpleFactory.getRandomLong().intValue());
        request.setData(LocalDate.now());
        return request;
    }
}
