package team.a5.gachigayu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team.a5.gachigayu.security.oauth2.OAuth2AuthenticationSuccessHandler;
import team.a5.gachigayu.security.oauth2.account.SignUpProcessManager;
import team.a5.gachigayu.security.token.TokenProvider;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration {

    private final TokenProvider tokenProvider;
    private final SignUpProcessManager signUpProcessManager;

    public OAuth2SecurityConfiguration(TokenProvider tokenProvider, SignUpProcessManager signUpProcessManager) {
        this.tokenProvider = tokenProvider;
        this.signUpProcessManager = signUpProcessManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        .requestMatchers("/api/authorization/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .successHandler(authenticationSuccessHandler())
                        .userInfoEndpoint(userInfo -> userInfo.userService(new DefaultOAuth2UserService())))
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(tokenProvider, signUpProcessManager);
    }
}
