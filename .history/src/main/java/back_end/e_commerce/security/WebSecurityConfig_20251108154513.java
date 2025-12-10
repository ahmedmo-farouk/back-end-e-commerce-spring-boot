package back_end.e_commerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        //     .csrf(csrf -> csrf.disable()) // إيقاف CSRF
        //     .authorizeHttpRequests(auth -> auth
        //         .requestMatchers("/auth/**").permitAll() // السماح للتسجيل والدخول بدون توكن
        //         .anyRequest().authenticated() // باقي الطلبات تحتاج JWT
        //     )
        //     .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // return http.build();
    }
}
