package com.example.school.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.Date;

public class StudentDTO {
    private String name;
    private Long rollNumber;
    private Character section;
    private LocalDate dob;
    private String address;


    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Long rollNumber) {
        this.rollNumber = rollNumber;
    }


    public Character getSection() {
        return section;
    }

    public void setSection(Character section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonCreator
    public StudentDTO(@JsonProperty("name") String name, @JsonProperty("rollNumber") Long rollNumber, @JsonProperty("dob") LocalDate dob, @JsonProperty("address") String address, @JsonProperty("section") Character section) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.section = section;
        this.address=address;
        this.dob=dob;
    }
}
