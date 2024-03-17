package com.laioffer.twitch.model.error;

public record TwitchErrorResponse(
        String message,
        String error,
        String details
) {
}
