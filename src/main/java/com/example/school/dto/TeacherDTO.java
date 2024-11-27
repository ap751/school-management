package com.example.school.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public class TeacherDTO {
    private String name;
    private String email;
    @NonNull
    private Long phoneNumber;
    private String qualification;
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @JsonCreator
    public TeacherDTO(@JsonProperty("name") String name, @JsonProperty("email") String email, @JsonProperty("phoneNumber") Long phoneNumber, @JsonProperty("qualification") String qualification, @JsonProperty("subject") String subject) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.subject=subject;
    }
}
