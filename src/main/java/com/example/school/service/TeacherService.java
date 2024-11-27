package com.example.school.service;

import com.example.school.dto.TeacherDTO;
import com.example.school.dto.UpdatePasswordDTO;
import com.example.school.entity.Teacher;
import com.example.school.entity.User;
import com.example.school.repository.TeacherRepository;
import com.example.school.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @CacheEvict(value = "teachers", allEntries = true)
    public void updateTeacher(Long phoneNumber, TeacherDTO teacherDTO) {
        if(!Objects.equals(teacherDTO.getPhoneNumber(), phoneNumber)){
            throw new IllegalArgumentException("Phone number not matched");
        }
        Optional<Teacher> teacherOpt = teacherRepository.findByPhoneNumber(phoneNumber);
        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            if(Objects.nonNull(teacherDTO.getName())){
                teacher.setName(teacherDTO.getName());
            }
            if(Objects.nonNull(teacherDTO.getEmail())){
                teacher.setEmail(teacherDTO.getEmail());
            }
            if(Objects.nonNull(teacherDTO.getQualification())){
                teacher.setQualification(teacherDTO.getQualification());
            }
            if(Objects.nonNull(teacherDTO.getSubject())){
                teacher.setSubject(teacherDTO.getSubject());
            }
            teacherRepository.save(teacher);
        } else {
            throw new RuntimeException("Teacher not found");
        }
    }

    @Cacheable(value = "teachers", unless = "#result == null || #result.isEmpty()")
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToTeacherDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    @CacheEvict(value = "teachers", allEntries = true)
    public void deleteTeacher(Long id) {
        userRepository.deleteByUsername(id.toString());
        teacherRepository.deleteByPhoneNumber(id);
    }

    private TeacherDTO convertToTeacherDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getName(),
                teacher.getEmail(),
                teacher.getPhoneNumber(),
                teacher.getQualification(),
                teacher.getSubject()
        );
    }
    public Long getPhoneByJwtToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        String username = jwtService.parseJwtToken(token.replace("Bearer ", ""));
        return Long.parseLong(username);
    }
    public Optional<Teacher> getTeacherByPhoneNumber(Long phoneNumber) {
        return teacherRepository.findByPhoneNumber(phoneNumber);
    }

    public void updatePassword(Long phoneNumber, UpdatePasswordDTO updatePasswordDTO) {
        Optional<User> userOptional=userRepository.findByUsername(phoneNumber.toString());
        if(userOptional.isPresent()){
            User user=userOptional.get();
            user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
            userRepository.save(user);
        }
    }
}

