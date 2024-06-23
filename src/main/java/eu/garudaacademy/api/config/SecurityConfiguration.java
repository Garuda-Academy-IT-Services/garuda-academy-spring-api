package eu.garudaacademy.api.config;

import eu.garudaacademy.api.config.filters.JwtRequestFilter;
import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.services.MysqlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MysqlUserDetailsService mysqlUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mysqlUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Add your allowed origins
        config.addAllowedOrigin("https://www.garudaacademy.eu"); // Add your allowed origins
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        configureAllowedPaths(http);
        configureTokenRequestFilter(http);
    }

    protected void configureAllowedPaths(final HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        ApiPaths.AUTHENTICATION_BASE + ApiPaths.AUTHENTICATION_AUTHENTICATE,
                        ApiPaths.AUTHENTICATION_BASE + ApiPaths.VERIFY_TOKEN,
                        ApiPaths.VIDEOS_BASE + ApiPaths.GET_ALL,
                        ApiPaths.VIDEOS_BASE + ApiPaths.VIDEOS_GET_BY_CATEGORY,
                        ApiPaths.VIDEOS_BASE + ApiPaths.GET_BY_ID,
                        ApiPaths.CATEGORIES_BASE + ApiPaths.GET_ALL,
                        ApiPaths.CATEGORIES_BASE + ApiPaths.GET_BY_ID,
                        ApiPaths.USERS_BASE + ApiPaths.CREATE)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    protected void configureTokenRequestFilter(final HttpSecurity http) {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
