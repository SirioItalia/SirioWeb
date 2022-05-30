package com.sirioitalia.api.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sirioitalia.api.embeddable.Address;
import com.sirioitalia.api.model.Role;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

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

        LocalDateTime getRegistrationDate();

        String getPhoneNumber();

        Address getAddress();

        Role getRole();

        Collection<OrderProjection.WithoutUserInfo> getOrders();

        Collection<RatingProjection.FromUser> getRatings();
    }

    interface Authentication {
        String getId();

        String getEmail();

        String getPasswordHash();

        String getPasswordSalt();


        @JsonProperty("roleLabel")
        @Value("#{target.getRole().getLabel()}")
        String getRoleLabel();
    }
}
