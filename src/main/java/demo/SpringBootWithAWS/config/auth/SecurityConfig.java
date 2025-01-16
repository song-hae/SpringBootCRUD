package demo.SpringBootWithAWS.config.auth;

import demo.SpringBootWithAWS.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig  {
    private final CustomOAuth2UserService customOAuth2UserService;

    private static final String[] AUTH_WHITELIST = {
            "/","/css/**","/images/**","/js/**","/h2-console/**"
    };

    private static final String[] AUTH_LIST = {
            "/api/v1/**"
    };

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Spring security 6.1 이상
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers-> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(AUTH_LIST).hasRole(Role.USER.name())
                .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)));

        return http.build();

        // 6.1 이전
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                .authorizeRequests()
//                .andMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
//                .andMatchers("/api/v1/**").hasRole(Role.USER.name())
//                .anyRequests().authenticated()
//                .and().logout().logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);
    }


}
