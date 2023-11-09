package com.novi.carcompany.configurations;

import com.novi.carcompany.filters.JwtRequestFilter;
import com.novi.carcompany.services.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfiguration(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/cars").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/cars/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/cars/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "cars").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "cars/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers(HttpMethod.POST, "/parts").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/parts/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/parts/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "parts").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "parts/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers(HttpMethod.POST, "/customers").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/customers/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/customers/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "customers").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "customers/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers(HttpMethod.POST, "/employees").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/employees/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/employees/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "employees").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "employees/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers(HttpMethod.POST, "/invoices").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/invoices/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/invoices/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "invoices").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "invoices/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "users/**").hasAnyAuthority("ADMIN", "USER")
                                //
                                .requestMatchers("/authenticated").authenticated()
                                .requestMatchers("/authenticate").permitAll()
                                //
                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
