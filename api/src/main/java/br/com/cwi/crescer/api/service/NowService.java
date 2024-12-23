package br.com.cwi.crescer.api.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class NowService {
    public LocalDateTime now(){
        return LocalDateTime.now();
    }
    public LocalDate nowDate(){
        return LocalDate.now();
    }
}
