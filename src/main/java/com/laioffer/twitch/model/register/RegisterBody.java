package com.laioffer.twitch.model.register;

import com.fasterxml.jackson.annotation.JsonProperty;

// used for user registration
public record RegisterBody(
        String username,
        String password,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName
) {
}
