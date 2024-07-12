package com.example.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Long id;
    @NotBlank(message = "User name is required")
    private String userName;
    @NotBlank(message = "Team is required")
    private String team;
    @NotNull(message = "Moves is required")
    private int moves;
    @NotNull(message = "Time is required")
    private long time;

    // Getters y Setters
}