// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import com.ledo.jwt.config.JwtConfig;
// import com.ledo.jwt.provider.JwtTokenProvider;
// @EnableWebSecurity
// @Configuration
// public class AuthSecurityConf {
//     @Value("${jwt.secret}")
//     private String secretKey;
//     @Value("${jwt.expiration}")
//     private long tokenExpiration;
//     @Bean
//     public JwtTokenProvider jwtTokenProvider() {
//         JwtConfig jwtConfig = new JwtConfig();
//         jwtConfig.setSecret(secretKey);
//         jwtConfig.setTtlMillis(tokenExpiration);
//         return new JwtTokenProvider(jwtConfig);
//     }
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
//         http
//             .cors(cors -> cors.disable())
//             .csrf(csrf -> csrf.disable())
//             .sessionManagement(session -> session
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/login").permitAll()
//                 .anyRequest().authenticated())
//             .addFilterBefore(new JwtTokenFilterConfigurer(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }}
// package com.example.API_Starter.security;
// import javax.sql.DataSource;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.provisioning.JdbcUserDetailsManager;
// import org.springframework.security.provisioning.UserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
// @Configuration
// public class AuthSecurityConf {
//     @Bean
//     public UserDetailsManager usersDetailsManager(DataSource ds) {
//         JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(ds);
//         jdbcUserDetailsManager.setDataSource(ds);
//         jdbcUserDetailsManager.setUsersByUsernameQuery(
//                 "select email, password, 'true' from users where email=?"
//         );
//         jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                 "select email, role from users where email=?"
//         );
//         return jdbcUserDetailsManager;
//     }
//     // disable csrf
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.csrf().disable();
//         return http.build();
//     }
// }
package com.example.API_Starter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthSecurityConf {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable()).headers().frameOptions().disable().and()
                .authorizeHttpRequests(request -> request
                .requestMatchers("/api/auth/login",
                        "/auth/login", "/v2/api-docs",
                        "/swagger-ui",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/h2-console/**",
                        "/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
