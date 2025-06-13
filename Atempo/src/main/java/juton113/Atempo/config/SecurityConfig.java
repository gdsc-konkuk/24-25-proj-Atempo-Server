package juton113.Atempo.config;

import juton113.Atempo.exception.CustomAccessDeniedHandler;
import juton113.Atempo.exception.CustomAuthenticationEntryPoint;
import juton113.Atempo.security.jwt.JwtAuthenticationFilter;
import juton113.Atempo.security.oauth.CustomAuthorizationRequestResolver;
import juton113.Atempo.security.oauth.CustomOauth2SuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(@Qualifier("customCorsConfigurationSource") CorsConfigurationSource corsConfigurationSource,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomOauth2SuccessHandler customOauth2SuccessHandler,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler,
                          ClientRegistrationRepository clientRegistrationRepository) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOauth2SuccessHandler = customOauth2SuccessHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource)
                )
                .csrf(csrf -> csrf
                        .disable()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html",
                                "api/v1/auth/login", "api/v1/auth/access-token", "api/v1/auth/refresh-token", "api/v1/twilio/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .authorizationRequestResolver(
                                        new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization")
                                )
                        )
                        .successHandler(customOauth2SuccessHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                );

        return http.build();
    }
}
