package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class SkierServicesTests {
    @Autowired
    ISkierServices skierServices;

    @Test
    public void addSkierTest() {

        //liste piste
        Set<Piste> listPiste= new HashSet<Piste>() {
            {
                Piste p1 = Piste.builder().namePiste("Ariana").numPiste(66L).length(15).color(Color.GREEN).slope(6).build();
                add(p1);
            }
        };


        //list registration
        Set<Registration> listRegistration= new HashSet<Registration>();


        //instance subscription
        Subscription sub = Subscription.builder().numSub(665L).typeSub(TypeSubscription.ANNUAL).price(1245F).startDate(LocalDate.of(2023,10,2)).endDate(LocalDate.of(2023,10,19)).build();

        Skier skier = Skier.builder().numSkier(1234567L).firstName("Hamzaoui").lastName("madamina").dateOfBirth(LocalDate.of(2023,10,19)).city("Paris").pistes(listPiste).subscription(sub).registrations(listRegistration).build();
        Skier savedSkier = skierServices.addSkier(skier);
        assertNotNull(savedSkier.getNumSkier());
        assertSame(skier.getFirstName(), savedSkier.getFirstName());
        assertSame(skier.getLastName(), savedSkier.getLastName());
        assertSame(skier.getDateOfBirth(), savedSkier.getDateOfBirth());

    }
}
