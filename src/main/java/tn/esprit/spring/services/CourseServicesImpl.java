package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class CourseServicesImpl implements  ICourseServices{

    private ICourseRepository courseRepository;

    @Override
    @Test
    @Order(0)
    public List<Course> retrieveAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Test
    @Order(1)
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Test
    @Order(2)
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Test
    @Order(3)
    public Course retrieveCourse(Long numCourse) {
        return courseRepository.findById(numCourse).orElse(null);
    }


}
