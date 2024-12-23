package br.com.cwi.crescer.api.controller.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PesoResponse {

    private LocalDate data;
    private String peso;
}
