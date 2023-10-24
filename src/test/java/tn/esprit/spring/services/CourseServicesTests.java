package tn.esprit.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class CourseServicesTests {
    @Autowired
    ICourseServices courceservice;

    @Test
    public void addCouceTest() {
        Course cource = Course.builder().numCourse(66L).level(7).typeCourse(TypeCourse.INDIVIDUAL).support(Support.SKI).price(14F).timeSlot(12).build();
        Course savedCource = courceservice.addCourse(cource);
        assertNotNull(savedCource.getNumCourse());
        assertSame(cource.getSupport(), savedCource.getSupport());
        assertSame(cource.getPrice(), savedCource.getPrice());
        assertSame(cource.getLevel(), savedCource.getLevel());

    }
}
