package com.example.school.repository;

import com.example.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByPhoneNumber(Long rollNumber);

    void deleteByPhoneNumber(Long id);
}

