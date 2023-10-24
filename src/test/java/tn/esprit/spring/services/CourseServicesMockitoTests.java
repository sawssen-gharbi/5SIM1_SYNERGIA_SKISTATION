package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class CourseServicesMockitoTests {
    Course cource = Course.builder().numCourse(66L).level(7).typeCourse(TypeCourse.INDIVIDUAL).support(Support.SKI).price(14F).timeSlot(12).build();

    @InjectMocks
    CourseServicesImpl courceService;
    ICourseRepository courseRepository= Mockito.mock(ICourseRepository.class);

    @Test
    public void retrieveCoucrce() {
        Mockito.when(courseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cource));
        Course cource1 = courceService.retrieveCourse((long) 66);
        assertNotNull(cource1);
        log.info("get ==> " + cource1.toString());
        verify(courseRepository).findById(Mockito.anyLong());

    }
}
