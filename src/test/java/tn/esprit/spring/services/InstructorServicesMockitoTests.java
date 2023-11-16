package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class InstructorServicesMockitoTests {

    //Objet
    Instructor instructor = Instructor.builder().numInstructor(1234567L).firstName("Sawssen").lastName("Gharbi").dateOfHire(LocalDate.of(2023,10,19)).build();

    //Mock dependencies
    @InjectMocks
    InstructorServicesImpl instructorServices;
    IInstructorRepository instructorRepository = Mockito.mock(IInstructorRepository.class);

    @Mock
    ICourseRepository courseRepository;

    //Tests

    @Test
    void updateInstructorMockito() {

        Mockito.when(instructorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(instructor));
        Instructor ins = instructorServices.retrieveInstructor(1234567L);
        assertEquals(ins.getNumInstructor(),instructor.getNumInstructor());
        Mockito.when(instructorRepository.save(Mockito.any(Instructor.class))).thenReturn(Instructor.builder().numInstructor(1234567L).firstName("Sawy").lastName("Gh").dateOfHire(LocalDate.of(2023,10,19)).build());
        Instructor updatedInstructor = instructorServices.addInstructor(instructor);
        assertNotNull(updatedInstructor);

    }

}


