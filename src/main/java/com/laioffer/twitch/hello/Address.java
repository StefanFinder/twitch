package com.laioffer.twitch.hello;

// by default help us create all private fields
// override HashCode, toString and getter.
public record Address(
        String street,
        String city,
        String state,
        String country
) {
}
