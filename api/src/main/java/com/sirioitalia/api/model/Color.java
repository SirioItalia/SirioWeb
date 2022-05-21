package com.sirioitalia.api.model;

import lombok.*;

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
@Getter
@Setter
@ToString
@Table(name = "colors")
public class Color implements Serializable {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Getter
    @Setter
    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @Pattern(message = "Color must be a valid hexadecimal format", regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    @Getter
    @Setter
    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "hexadecimalCode", nullable = false, unique = true)
    private String hexadecimalCode;
}
