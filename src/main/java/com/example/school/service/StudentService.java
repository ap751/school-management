package com.example.school.service;

import com.example.school.dto.StudentDTO;
import com.example.school.dto.UpdatePasswordDTO;
import com.example.school.entity.Student;
import com.example.school.entity.User;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @CacheEvict(value = "students", allEntries = true)
    public void updateStudent(Long rollNumber, StudentDTO studentDTO) {
        if(!Objects.equals(studentDTO.getRollNumber(), rollNumber)){
            throw new IllegalArgumentException("roll Number not matched");
        }
        Optional<Student> studentOpt = studentRepository.findByRollNumber(rollNumber);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if(Objects.nonNull(studentDTO.getName())){
                student.setName(studentDTO.getName());
            }
            if(Objects.nonNull(studentDTO.getDob())){
                student.setDob(studentDTO.getDob());
            }
            if(Objects.nonNull(studentDTO.getSection())){
                student.setSection(studentDTO.getSection());
            }
            if(Objects.nonNull(studentDTO.getAddress())){
                student.setAddress(studentDTO.getAddress());
            }
            studentRepository.save(student);
        } else {
            throw new ResourceNotFoundException("Student with Roll number " + rollNumber + " not found");
        }
    }
    @Transactional
    @CacheEvict(value = "students", allEntries = true)
    public void deleteStudent(Long id) {
        userRepository.deleteByUsername(id.toString());
        studentRepository.deleteByRollNumber(id);
    }


    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }


    public Optional<Student> getStudentByRollNumber(Long rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }
  public Long getRollNumberByJwtToken(HttpServletRequest request){
      String token = request.getHeader("Authorization");
      if (token == null || !token.startsWith("Bearer ")) {
          return null;
      }
      String username = jwtService.parseJwtToken(token.replace("Bearer ", ""));
      return Long.parseLong(username);
  }

    public void updatePassword(Long rollNumber, UpdatePasswordDTO updatePasswordDTO) {
         Optional<User> userOptional=userRepository.findByUsername(rollNumber.toString());
         if(userOptional.isPresent()){
             User user=userOptional.get();
             user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
             userRepository.save(user);
         }

    }

    @Cacheable(value = "students", unless = "#result == null || #result.isEmpty()")
    public List<StudentDTO> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }
    private StudentDTO convertToStudentDTO(Student student) {
        return new StudentDTO(
                student.getName(),
                student.getRollNumber(),
                student.getDob(),
                student.getAddress(),
                student.getSection()
        );
    }
}

