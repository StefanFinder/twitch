package com.laioffer.twitch.controller;

import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.service.FavoriteService;
import com.laioffer.twitch.model.favorite.FavoriteRequestBody;
import com.laioffer.twitch.model.item.TypeGroupedItemList;
import com.laioffer.twitch.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {


    private final FavoriteService favoriteService;
    private final UserService userService;


    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    // 此处的user是从当前的security拿到的，即当前登陆的用户的所有喜欢的东西
    // 从db拿一系列ItemEntity，并且返回的TypeGroupedItemList是对这些不同的Item分类了
    @GetMapping
    public TypeGroupedItemList getFavoriteItems(@AuthenticationPrincipal User user) {
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        return favoriteService.getGroupedFavoriteItems(userEntity);
    }

    // 此处的user是authentication提供的，只包含username和password
    @PostMapping
    public void setFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        favoriteService.setFavoriteItem(userEntity, body.favorite());
    }


    @DeleteMapping
    public void unsetFavoriteItem(@AuthenticationPrincipal User user, @RequestBody FavoriteRequestBody body) {
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
    }

}
