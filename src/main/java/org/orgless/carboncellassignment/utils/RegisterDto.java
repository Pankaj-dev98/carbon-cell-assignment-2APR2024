package org.orgless.carboncellassignment.utils;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RegisterDto {
    @Email(message = "Username must be a valid email")
    @NotBlank(message = "Username must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}
