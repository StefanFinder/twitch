package com.laioffer.twitch.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        String name,
        String company,

        // twitch 使用的是snake case定义的变量名。此处定了了一个java camel case和json snake case 变量的双向转化
        @JsonProperty("home_address") Address homeAddress,
        @JsonProperty("favorite_book") Book favoriteBook
) {
}
