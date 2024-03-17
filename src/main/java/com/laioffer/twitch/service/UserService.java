package com.laioffer.twitch.service;

import com.laioffer.twitch.db.entity.UserEntity;
import com.laioffer.twitch.db.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Transactional
    public void register(String username, String password, String firstName, String lastName) {
        // userdetaiilsManager 管理的是register相关的。此时我们使用userdetailsmanager将这个对象存到db
        // 但是firstname和lastname是空缺的
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")  // 此处的role是用于该用户的authorization，后面会插入到authorities这张表里
                .build();
        userDetailsManager.createUser(user); // 调用userDetailsManager的话会操作两张表，1.插入到users。 2. 插入到authorities里，同时会将上面的role加上去
        // 所以此时我们使用repository将firstname 和lastname更新一下
        userRepository.updateNameByUsername(username, firstName, lastName);
    }


    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
