package com.laioffer.twitch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Appconfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 暂时停止security,因为我们使用了security的auth2, 不然/hello都需要验证
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll() // 这一行从跟路径允许所有的请求
                );
        return http.build();
    }

}
