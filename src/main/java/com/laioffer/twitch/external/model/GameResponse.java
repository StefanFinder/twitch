package com.laioffer.twitch.external.model;

import java.util.List;

// this is for getting a list of games, it could be used to store either one or a list
// when we are querying /games in twitch API, this will take one game
// when we are querying /games/top in twitch API, this will take a list of games
public record GameResponse(
        List<Game> data

) {
}
