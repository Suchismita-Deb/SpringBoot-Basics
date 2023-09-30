package com.springHelloWorld.service;

import com.springHelloWorld.dto.StudentPgDto;
import com.springHelloWorld.dto.StudentSave;
import com.springHelloWorld.mapper.StudentMapper;
import com.springHelloWorld.model.Student;
import com.springHelloWorld.dao.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceWithPg {
    private StudentRepository studentRepository;
    private StudentMapper mapper;

    @Autowired
    public StudentServiceWithPg(@Qualifier("jpaStudentRepository") StudentRepository studentRepository, StudentMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public List<StudentPgDto> getStudentById()  {
        List<Student> maleStudents = studentRepository.findMaleStudents();

        //Convert the DB Model into Response DTO
        /*List<StudentPgDto> studentPgDtos = maleStudents.stream()
                //.map()//TODO:
                .collect(Collectors.toList());
*/
        return null;
    }

    public int saveStudent(StudentSave studentSave){
        Student student = Student.builder()
                .id(4000)//Assign an id to be saved in the DB
                .firstName(studentSave.firstName())
                .lastName(studentSave.lastName())
                .cityofbirth(studentSave.cityofbirth())
                .dob(studentSave.dob())
                .email(studentSave.email())
                .email(studentSave.gender())
                .university(studentSave.university())
                .build();

        Student savedStudent = studentRepository.save(student);
        return savedStudent.getId();
    }
}
