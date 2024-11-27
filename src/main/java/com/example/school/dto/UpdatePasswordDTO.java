package com.example.school.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
@Data
public class UpdatePasswordDTO {
    @NonNull
    private String newPassword;
    @JsonCreator
    public UpdatePasswordDTO(@JsonProperty("newPassword") String newPassword) {
        this.newPassword = newPassword;
    }
}

