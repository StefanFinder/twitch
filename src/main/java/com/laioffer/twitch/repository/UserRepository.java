package com.laioffer.twitch.repository;

import com.laioffer.twitch.db.entity.UserEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
    List<UserEntity> findByLastName(String lastName);


    List<UserEntity> findByFirstName(String firstName);


    UserEntity findByUsername(String username);

    @Modifying
    @Query("UPDATE users SET password = :password WHERE id = :id")
    void updatePasswrodById(@Param("id") Long id, @Param("password") String password);


    @Modifying
    @Query("UPDATE users SET first_name = :firstName, last_name = :lastName WHERE username = :username")
    void updateNameByUsername(@Param("username") String username, @Param("firstName") String firstName, @Param("lastName") String lastName);

}
