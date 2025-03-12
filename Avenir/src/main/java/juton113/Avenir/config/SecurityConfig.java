package juton113.Avenir.config;

import juton113.Avenir.security.oauth.CustomOauth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // TODO: 규모 증가 시 white list 만들어 관리 요망
                        .requestMatchers("/", "/index.html", "/auth/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                // -----[start: h2 console]------
                .headers(headers -> headers
                        .frameOptions().disable()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                // -----[end: h2 console]-----
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new CustomOauth2SuccessHandler())
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                );

        return http.build();
    }
}
