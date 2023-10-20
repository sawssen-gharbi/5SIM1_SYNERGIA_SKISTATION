package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Instructor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class InstructorServicesTests {

    @Autowired
    IInstructorServices instructorServices;

    @Test
    public void addInstructorTest() {
        Instructor instructor = Instructor.builder().numInstructor(234567L).firstName("Iris").lastName("Saw").dateOfHire(LocalDate.of(2023,10,10)).build();
        Instructor savedInstructor = instructorServices.addInstructor(instructor);
        assertNotNull(savedInstructor.getNumInstructor());
        assertSame(instructor.getFirstName(), savedInstructor.getFirstName());
        assertSame(instructor.getLastName(), savedInstructor.getLastName());
        assertSame(instructor.getDateOfHire(), savedInstructor.getDateOfHire());

    }



}