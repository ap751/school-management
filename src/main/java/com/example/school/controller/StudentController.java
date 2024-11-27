package com.example.school.controller;

import com.example.school.dto.ResponseDTO;
import com.example.school.dto.StudentDTO;
import com.example.school.dto.TeacherDTO;
import com.example.school.dto.UpdatePasswordDTO;
import com.example.school.exception.UnauthorizedAccessException;
import com.example.school.service.JwtService;
import com.example.school.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtService jwtService;


@GetMapping("/get")
public ResponseEntity<StudentDTO> getStudent(HttpServletRequest request) {

    Long rollNumber=studentService.getRollNumberByJwtToken(request);
    if(rollNumber==null){
        throw new UnauthorizedAccessException("Unauthorized access: No valid student found in JWT token");
    }

    return studentService.getStudentByRollNumber(rollNumber)
            .map(student -> ResponseEntity.ok(new StudentDTO(
                    student.getName(),
                    student.getRollNumber(),
                    student.getDob(),
                    student.getAddress(),
                    student.getSection())))
            .orElse(ResponseEntity.notFound().build());
}


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateStudent(HttpServletRequest request, @RequestBody @Validated StudentDTO studentDTO) {
        Long rollNumber=studentService.getRollNumberByJwtToken(request);
        if(rollNumber==null){
            throw new UnauthorizedAccessException("Unauthorized access: No valid student found in JWT token");
        }
        studentService.updateStudent(rollNumber, studentDTO);
        return ResponseEntity.ok(new ResponseDTO("Student updated successfully", 200));
    }

    @PatchMapping("/update-password")
    public ResponseEntity<ResponseDTO> updatePassword(HttpServletRequest request,
                                                 @RequestBody  @Validated UpdatePasswordDTO updatePasswordDTO) {
        Long rollNumber=studentService.getRollNumberByJwtToken(request);
        if(rollNumber==null){
            throw new UnauthorizedAccessException("Unauthorized access: No valid student found in JWT token");
        }
        studentService.updatePassword(rollNumber, updatePasswordDTO);
        return ResponseEntity.ok(new ResponseDTO("Password updated successfully!", 200));
    }

    @GetMapping("/student-list")
    public List<StudentDTO> getAllStudent() {
        return studentService.getAllStudent();
    }

}
