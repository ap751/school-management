package com.example.school.controller;

import com.example.school.dto.ResponseDTO;
import com.example.school.dto.StudentDTO;
import com.example.school.dto.TeacherDTO;
import com.example.school.dto.UpdatePasswordDTO;
import com.example.school.exception.UnauthorizedAccessException;
import com.example.school.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/get")
    public ResponseEntity<TeacherDTO> getStudent(HttpServletRequest request) {

        Long rollNumber=teacherService.getPhoneByJwtToken(request);
        if(rollNumber==null){
            throw new UnauthorizedAccessException("Unauthorized access: No valid user number found in JWT token");
        }
        return teacherService.getTeacherByPhoneNumber(rollNumber)
                .map(teacher -> ResponseEntity.ok(new TeacherDTO(
                        teacher.getName(),
                        teacher.getEmail(),
                        teacher.getPhoneNumber(),
                        teacher.getQualification(),
                        teacher.getSubject())))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateStudent(HttpServletRequest request, @RequestBody @Validated TeacherDTO teacherDTO) {
        Long rollNumber=teacherService.getPhoneByJwtToken(request);
        if(rollNumber==null){
            throw new UnauthorizedAccessException("Unauthorized access: No valid user found in JWT token");
        }
        teacherService.updateTeacher(rollNumber, teacherDTO);
        return ResponseEntity.ok(new ResponseDTO("Teacher updated successfully", 200));
    }

    @GetMapping("/teacher-list")
    public List<TeacherDTO> getAllTeacher() {
        return teacherService.getAllTeachers();
    }

    @PatchMapping("/update-password")
    public ResponseEntity<ResponseDTO> updatePassword(HttpServletRequest request,
                                                 @RequestBody  @Validated UpdatePasswordDTO updatePasswordDTO) {
        Long rollNumber=teacherService.getPhoneByJwtToken(request);
        if (rollNumber == null) {
            throw new UnauthorizedAccessException("Unauthorized access: No valid user found in JWT token");
        }
        teacherService.updatePassword(rollNumber, updatePasswordDTO);
        return ResponseEntity.ok(new ResponseDTO("Password updated successfully!", 200));
    }
}
