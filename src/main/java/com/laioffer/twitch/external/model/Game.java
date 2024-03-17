package com.laioffer.twitch.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// java entity to map to json game
public record Game(
        String id,
        String name,
        @JsonProperty("box_art_url") String boxArtUrl,
        String igdb_id
) {
}
