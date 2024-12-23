package br.com.cwi.crescer.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class NowServiceTest {

    @InjectMocks
    private NowService nowService;

    @Test
    @DisplayName("Deve retornar data atual corretamente")
    void deveRetornarDataAtual(){
        LocalDate expected = LocalDate.now();
        LocalDate actual = nowService.nowDate();

        assertEquals(expected, actual);
    }

}
