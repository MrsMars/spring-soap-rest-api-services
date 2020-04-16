package com.aoher.service;

import com.aoher.bean.Course;
import com.aoher.courses.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDetailsService {

    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        Course course2 = new Course(2, "Spring MVC", "10 Examples");
        Course course3 = new Course(3, "Spring Boot", "6K Students");
        Course course4 = new Course(4, "Maven", "Most popular maven course on internet!");

        courses.addAll(Arrays.asList(course1, course2, course3, course4));
    }

    // course - 1
    public Course findById(int id) {
        return courses.stream().filter(course -> course.getId() == id).findFirst().orElse(null);
    }

    // courses
    public List<Course> findAll() {
        return courses;
    }

    public Status deleteById(int id) {
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getId() == id) {
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }
}
