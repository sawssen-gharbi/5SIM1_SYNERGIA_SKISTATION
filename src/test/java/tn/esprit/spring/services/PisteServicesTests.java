package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class PisteServicesTests {
    @Autowired
    IPisteServices pisteServices;

    @Test
    public void addInstructorTest() {
        Piste piste = Piste.builder().namePiste("Ariana").numPiste(66L).color(Color.GREEN).slope(6).build();
        Piste savedPiste = pisteServices.addPiste(piste);
        assertNotNull(savedPiste.getNumPiste());
        assertSame(piste.getNamePiste(), savedPiste.getNamePiste());
        assertSame(piste.getSlope(), savedPiste.getSlope());
        assertSame(piste.getColor(), savedPiste.getColor());

    }

}
