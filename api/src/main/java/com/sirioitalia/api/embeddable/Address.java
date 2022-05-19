package com.sirioitalia.api.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
public class Address {
    @NotNull
    @NotBlank
    @NotEmpty
    @Getter
    @Setter
    @Column(name = "\"streetName\"")
    private String streetName;

    @NotNull
    @NotEmpty
    @NotBlank
    @Getter
    @Setter
    @Column(name = "\"streetType\"")
    private String streetType;

    @NotNull
    @NotBlank
    @NotEmpty
    @Getter
    @Setter
    @Column(name = "\"streetNumber\"", nullable = false)
    private String streetNumber;



    @NotNull
    @NotBlank(message = "City field cannot be blank")
    @NotEmpty(message = "City field cannot be empty")
    @Getter
    @Setter
    private String city;

    @Pattern(regexp = "/^(([0-8][0-9])|(9[0-5]))[0-9]{3}$/")
    @Getter
    @Setter
    @Column(name = "\"zipCode\"")
    private String zipCode;
}
