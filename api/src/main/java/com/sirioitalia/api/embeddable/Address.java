package com.sirioitalia.api.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "\"streetName\"")
    private String streetName;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "\"streetType\"")
    private String streetType;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "\"streetNumber\"", nullable = false)
    private String streetNumber;


    @NotNull
    @NotBlank(message = "City field cannot be blank")
    @NotEmpty(message = "City field cannot be empty")
    private String city;

    @Pattern(regexp = "/^(([0-8][0-9])|(9[0-5]))[0-9]{3}$/")
    @Column(name = "\"zipCode\"")
    private String zipCode;
}
