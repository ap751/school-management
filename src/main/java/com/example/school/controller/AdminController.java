package com.example.school.controller;

import com.example.school.dto.ResponseDTO;
import com.example.school.dto.StudentDTO;
import com.example.school.dto.TeacherDTO;
import com.example.school.service.AuthService;
import com.example.school.service.StudentService;
import com.example.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @DeleteMapping ("/student/{id}")
    public ResponseEntity<ResponseDTO> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseDTO("Student deleted successfully", 200));
    }

    @PutMapping("/student/update-student")
    public ResponseEntity<ResponseDTO> updateTeacherById(@RequestBody @Validated StudentDTO studentDTO) {
        studentService.updateStudent(studentDTO.getRollNumber(),studentDTO);
        return ResponseEntity.ok(new ResponseDTO("Student updated successfully", 200));
    }

    @PostMapping("student/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody StudentDTO studentDTO) {
        authService.registerStudent(studentDTO);
        return ResponseEntity.ok(new ResponseDTO("Student registered successfully", 200));
    }

    @DeleteMapping ("/teacher/{id}")
    public ResponseEntity<ResponseDTO> deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(new ResponseDTO("Teacher deleted successfully", 200));
    }

    @PutMapping("/teacher/update-teacher")
    public ResponseEntity<ResponseDTO> updateTeacherById(@RequestBody @Validated TeacherDTO teacherDTO) {
        teacherService.updateTeacher(teacherDTO.getPhoneNumber(),teacherDTO);
        return ResponseEntity.ok(new ResponseDTO("Teacher updated successfully", 200));
    }


    @PostMapping("teacher/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody TeacherDTO teacherDTO) {
        authService.registerTeacher(teacherDTO);
        return ResponseEntity.ok(new ResponseDTO("Teacher registered successfully", 200));
    }

}
