package org.example.moviemate.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.moviemate.model.validation.OnCreate;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    @NotBlank(groups = OnCreate.class, message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 chars")
    private String username;

    @NotBlank(groups = OnCreate.class, message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    private List<String> watchlist;
    private List<String> diary;
}