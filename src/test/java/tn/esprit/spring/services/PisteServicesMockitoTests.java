package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j

public class PisteServicesMockitoTests {
    Piste piste = Piste.builder().namePiste("Ariana").numPiste(66L).color(Color.GREEN).slope(6).build();

    @InjectMocks
    PisteServicesImpl pisteServices;
    IPisteRepository  pisteRepository= Mockito.mock(IPisteRepository.class);

    @Test
    public void retrievePiste() {
        Mockito.when(pisteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(piste));
        Piste piste1 = pisteServices.retrievePiste((long) 66);
        assertNotNull(piste1);
        log.info("get ==> " + piste1.toString());
        verify(pisteRepository).findById(Mockito.anyLong());

    }
}
