package com.sirioitalia.api.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sirioitalia.api.embeddable.Address;
import com.sirioitalia.api.model.Role;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.Set;

public interface UserProjection {
    interface Short {
        Long getId();

        String getFirstName();

        String getLastName();
    }

    interface Full {
        Long getId();

        String getFirstName();

        String getLastName();

        @JsonProperty("fullName")
        @Value("#{target.getFirstName()} #{target.getLastName()}")
        String getFullName();

        String getEmail();

        LocalDate getBirthDate();

        String getPhoneNumber();

        Address getAddress();

        Role getRole();

        Set<OrderProjection> getOrders();
    }
}
