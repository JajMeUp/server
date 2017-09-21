package com.jajteam.jajmeup.configuration;

import com.jajteam.jajmeup.security.JwtAuthenticationProvider;
import com.jajteam.jajmeup.security.JwtFilter;
import com.jajteam.jajmeup.security.UnauthorizedAttemptHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    private JwtFilter buildJwtAuthenticationProvider() {
        JwtFilter filter = new JwtFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedAttemptHandler())
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
            .and()
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .addFilterBefore(buildJwtAuthenticationProvider(), UsernamePasswordAuthenticationFilter.class);
    }
}
