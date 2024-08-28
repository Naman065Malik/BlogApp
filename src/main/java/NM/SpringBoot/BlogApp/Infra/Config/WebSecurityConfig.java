package NM.SpringBoot.BlogApp.Infra.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import NM.SpringBoot.BlogApp.Infra.Config.Security.JwtAuthenticationFilter;

@Configuration
public class WebSecurityConfig{

   @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; 
    
    @Bean
    SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests((request) -> request
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults())
            .csrf((csrf) -> csrf.disable())
            .headers((header) -> header.frameOptions(Customizer.withDefaults()).disable());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        BCryptVersion bCryptVersion = BCryptVersion.$2B;
        return new BCryptPasswordEncoder(bCryptVersion);
    }
}
