package com.laioffer.twitch.service;

import com.laioffer.twitch.db.entity.FavoriteRecordEntity;
import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.repository.FavoriteRecordRepository;
import com.laioffer.twitch.repository.ItemRepository;
import com.laioffer.twitch.model.error.DuplicateFavoriteException;
import com.laioffer.twitch.model.item.TypeGroupedItemList;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteService {
    private final ItemRepository itemRepository;
    private final FavoriteRecordRepository favoriteRecordRepository;


    public FavoriteService(ItemRepository itemRepository,
                           FavoriteRecordRepository favoriteRecordRepository) {
        this.itemRepository = itemRepository;
        this.favoriteRecordRepository = favoriteRecordRepository;
    }

    // 根据user，如果此时user调用了这个方法，那就清空cache，下次直接去db里取数据
    // 此时因为我们的输入有两个值， user和twitch ID，如果我们不明确key是user的话，那么item会和user合到一起来
    // 清空cache，这样是找不到的，因为我们只需要根据不同的用户来清空对于的cache，所以此时key是user
    @CacheEvict(cacheNames = "recommend_items", key = "#user")
    @Transactional
    public void setFavoriteItem(UserEntity user, ItemEntity item) {
        ItemEntity persistedItem = itemRepository.findByTwitchId(item.twitchId());
        if (persistedItem == null) {
            persistedItem = itemRepository.save(item);
        }
        if (favoriteRecordRepository.existsByUserIdAndItemId(user.id(), persistedItem.id())) {
            throw new DuplicateFavoriteException();
        }
        FavoriteRecordEntity favoriteRecord = new FavoriteRecordEntity(null, user.id(), persistedItem.id(), Instant.now());
        favoriteRecordRepository.save(favoriteRecord);
    }

    // 根据user，如果此时user调用了这个方法，那就清空cache，下次直接去db里取数据
    // 此时因为我们的输入有两个值， user和twitch ID，如果我们不明确key是user的话，那么twicthID会和user合到一起来
    // 清空cache，这样是找不到的，因为我们只需要根据不同的用户来清空对于的cache，所以此时key是user

    @CacheEvict(cacheNames = "recommend_items", key = "#user")
    public void unsetFavoriteItem(UserEntity user, String twitchId) {
        ItemEntity item = itemRepository.findByTwitchId(twitchId);
        if (item != null) {
            favoriteRecordRepository.delete(user.id(), item.id());
        }
    }

    public List<ItemEntity> getFavoriteItems(UserEntity user) {
        List<Long> favoriteItemIds = favoriteRecordRepository.findFavoriteItemIdsByUserId(user.id());
        return itemRepository.findAllById(favoriteItemIds);
    }


    public TypeGroupedItemList getGroupedFavoriteItems(UserEntity user) {
        List<ItemEntity> items = getFavoriteItems(user);
        return new TypeGroupedItemList(items);
    }
}
