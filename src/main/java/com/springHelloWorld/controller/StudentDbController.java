package com.springHelloWorld.controller;

import com.springHelloWorld.dto.StudentDto;
import com.springHelloWorld.dto.StudentDtoClass;
import com.springHelloWorld.dto.StudentRequestBody;
import com.springHelloWorld.exception.GlobalExceptionHandler;
import com.springHelloWorld.exception.StudentNotFoundException;
import com.springHelloWorld.service.StudentServiceWithDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.rmi.StubNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController(value = "Rest controller for student with Db")
@RequestMapping("/student/db")
public class StudentDbController {
    @Autowired
    StudentServiceWithDb studentDbService;

    @GetMapping(value = "/{studentId}", path = "/{studentId}",
            consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { "application/json", MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_PDF_VALUE})
    public StudentDto getStudentById(@PathVariable String studentId){
        int studentIntId = Integer.valueOf(studentId);
        StudentDto studentDetailById = null;
        try {
            studentDetailById = studentDbService.getStudentById(studentIntId);
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return studentDetailById;
    }

    @RequestMapping(path = "/requestMapping/{studentId}",
            method = RequestMethod.GET,
            produces = { "application/json", MediaType.APPLICATION_XML_VALUE,  MediaType.APPLICATION_PDF_VALUE})
    public @ResponseBody StudentDto getStudentByIdRequestMapping(@PathVariable String studentId){
        int studentIntId = Integer.valueOf(studentId);
        StudentDto studentDetailById = null;
        try {
            studentDetailById = studentDbService.getStudentById(studentIntId);
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return studentDetailById;
    }

    @PostMapping(path = "/studentIdsByMap",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {"application/json"})
    public List<StudentDto> getStudentByIdsByMap(@RequestBody Map<String,List<Integer>> mapStudentIds) throws StudentNotFoundException {
        List<Integer> studentIdList = mapStudentIds.get("studentIds");
        List<StudentDto> studentDetailById = studentDbService.getStudentByIds(studentIdList);
        return studentDetailById;
    }

    @PostMapping(path = "/studentIdsByClassName",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {"application/json"})
    public StudentDtoClass getStudentByIdsRequestBody(@RequestBody StudentRequestBody studentRequestBody){

        List<String> studentIdStringList = studentRequestBody.getStudentIdList();
        System.out.println(studentIdStringList);
        List<Integer> studentIdList = studentIdStringList
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<StudentDto> studentDetailById = studentDbService.getStudentByIds(studentIdList);
        StudentDtoClass studentDtoClassReturn = StudentDtoClass.builder()
                .count(studentDetailById.size())
                .studentDtoList(studentDetailById)
                .build();

        return studentDtoClassReturn;
    }
}
