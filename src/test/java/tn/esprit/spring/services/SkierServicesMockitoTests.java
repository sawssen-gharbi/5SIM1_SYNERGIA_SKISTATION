package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class SkierServicesMockitoTests {
    Skier skier = Skier.builder().numSkier(1234567L).firstName("Hamzaoui").lastName("madamina").dateOfBirth(LocalDate.of(2023,10,19)).city("Paris").build();
    @InjectMocks
    SkierServicesImpl skierServices;
    ISkierRepository skierRepository = Mockito.mock(ISkierRepository.class);
    @Test
    public void retrievePiste() {
        Mockito.when(skierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(skier));
        Skier skie = skierServices.retrieveSkier((long) 66);
        assertNotNull(skie);
        log.info("get ==> " + skie.toString());
        verify(skierRepository).findById(Mockito.anyLong());

    }
}

