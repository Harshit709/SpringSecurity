package com.Security.demo.SpringSecurity.config;

import com.Security.demo.SpringSecurity.Filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig  {

    //CORS configuration
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                //this below line of the to send the sessin token so we can create and send jwt token to the end user
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:56961"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        //for jwt header changing configuration
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/notices", "/contact", "/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf->csrf.disable());
//
//        http.authorizeHttpRequests((requests) ->
//                requests.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans","/user").authenticated());
//
//        http.authorizeHttpRequests((request) ->
//                request.requestMatchers("/notices", "/contact", "/register").permitAll());
//
//        http.formLogin(withDefaults());
//        http.httpBasic(withDefaults());
//
//        // Return the SecurityFilterChain
//        return http.build();
//    }


    //  Approach 1 for making multiple user and there password for authantication and also we used defaultpasswordrncorder()

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder().
//                username("harsh").
//                password("abc")
//                .authorities("admin").build();
//
//        UserDetails userDetails1 = User.withDefaultPasswordEncoder().
//                username("mohit").password("pqr")
//                .authorities("read").build();
//        return new InMemoryUserDetailsManager(userDetails, userDetails1);
// here is User is an spring boot class and UserDetails is an interface and User class Implements UserDetails.


    //Approach 2 where we use NoOpPasswordEncoder bean while  creating  the userDetails
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails userDetails = User.withUsername("harsh")
//                .password("abc")
//                .authorities("admin").build();
//
//        UserDetails userDetails1 = User.withUsername("mohit")
//                .password("pqr")
//                .authorities("read").build();
//        return new InMemoryUserDetailsManager(userDetails, userDetails1);
//
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return NoOpPasswordEncoder.getInstance();
//    }

    //*jdbc security type connect to the database
//     @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource)
//    {
//        return new JdbcUserDetailsManager(dataSource);
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}