package com.laioffer.twitch.model.favorite;

import com.laioffer.twitch.db.entity.ItemEntity;

public record FavoriteRequestBody(
        ItemEntity favorite

) {
}
