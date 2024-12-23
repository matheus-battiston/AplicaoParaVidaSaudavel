package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DataRequest {
    @NotNull
    private LocalDate data;
}
