package com.example.school.service;


import com.example.school.dto.StudentDTO;
import com.example.school.dto.TeacherDTO;
import com.example.school.dto.UserDTO;
import com.example.school.entity.Role;
import com.example.school.entity.Student;
import com.example.school.entity.Teacher;
import com.example.school.entity.User;
import com.example.school.dto.LoginDTO;
import com.example.school.exception.CustomException;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.TeacherRepository;
import com.example.school.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.spi.ServiceRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Transactional
    @CacheEvict(value = "students", allEntries = true)
    public void registerStudent(StudentDTO studentDTO) {
        if(studentRepository.findByRollNumber(studentDTO.getRollNumber()).isPresent()){
            throw new CustomException("Roll Number already present");
        }
        User user = new User();
        user.setUsername(studentDTO.getRollNumber().toString());
        String password = formatDOBToPassword(studentDTO.getDob());
        user.setPassword(passwordEncoder.encode(password));

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setDob(studentDTO.getDob());
        student.setSection(studentDTO.getSection());
        student.setRollNumber(studentDTO.getRollNumber());
        student.setAddress(studentDTO.getAddress());
        studentRepository.save(student);
        user.setRole(Role.STUDENT);
        userRepository.save(user);
    }
    public void registerTeacher(TeacherDTO teacherDTO) {
        if(teacherRepository.findByPhoneNumber(teacherDTO.getPhoneNumber()).isPresent()){
            throw new CustomException("Phone Number already present");
        }
        User user = new User();
        user.setUsername(teacherDTO.getPhoneNumber().toString());
        String password = teacherDTO.getPhoneNumber().toString();
        user.setPassword(passwordEncoder.encode(password));
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setQualification(teacherDTO.getQualification());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setSubject(teacherDTO.getSubject());
        teacherRepository.save(teacher);
        user.setRole(Role.TEACHER);
        userRepository.save(user);
    }

    private String formatDOBToPassword(LocalDate dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return dob.format(formatter);
    }

    public String loginUser(LoginDTO loginDTO) {
        if (adminUsername.equals(loginDTO.getUsername()) && adminPassword.equals(loginDTO.getPassword())) {
            return jwtService.generateTokenForAdmin();
        }
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String role = (user.getRole()==Role.STUDENT) ? "STUDENT" : "TEACHER";
        return jwtService.generateJwtToken(user.getUsername(), role);

    }




}

