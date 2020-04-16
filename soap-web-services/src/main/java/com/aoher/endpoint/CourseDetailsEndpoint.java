package com.aoher.endpoint;

import com.aoher.bean.Course;
import com.aoher.courses.*;
import com.aoher.exception.CourseNotFoundException;
import com.aoher.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    private CourseDetailsService service;

    @PayloadRoot(namespace = "http://aoher.com/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
        Course course = service.findById(request.getId());

        if (course == null) {
            throw new CourseNotFoundException("Invalid Course Id " + request.getId());
        }

        return mapCourseDetails(course);
    }

    @PayloadRoot(namespace = "http://aoher.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
        List<Course> courses = service.findAll();
        return mapAllCourseDetails(courses);
    }

    @PayloadRoot(namespace = "http://aoher.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

        Status status = service.deleteById(request.getId());

        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));

        return response;
    }

    private GetCourseDetailsResponse mapCourseDetails(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        courses.stream().map(this::mapCourse).forEachOrdered(mapCourse -> response.getCourseDetails().add(mapCourse));
        return response;
    }

    private CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();

        courseDetails.setId(course.getId());

        courseDetails.setName(course.getName());

        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }

    private com.aoher.courses.Status mapStatus(Status status) {
        if (status == Status.FAILURE) return Status.FAILURE;
        return com.aoher.courses.Status.SUCCESS;
    }
}
