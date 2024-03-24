package com.laioffer.twitch.repository;

import com.laioffer.twitch.db.entity.FavoriteRecordEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FavoriteRecordRepository extends ListCrudRepository<FavoriteRecordEntity, Long> {
    List<FavoriteRecordEntity> findAllByUserId(Long userId);

    // check if a user liked an item
    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    // check all itemID liked by an user
    @Query("SELECT item_id FROM favorite_records WHERE user_id = :userId")
    List<Long> findFavoriteItemIdsByUserId(Long userId);

    // delete record which has corresponding userId and itemId
    // 修改
    @Modifying
    @Query("DELETE FROM favorite_records WHERE user_id = :userId AND item_id = :itemId")
    void delete(Long userId, Long itemId);
}
