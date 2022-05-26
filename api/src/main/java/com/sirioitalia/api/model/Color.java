package com.sirioitalia.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "colors")
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String label;

    @Pattern(message = "Color must be a valid hexadecimal format", regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "\"hexadecimalCode\"", nullable = false, unique = true)
    private String hexadecimalCode;
}
