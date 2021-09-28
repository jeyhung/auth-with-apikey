package io.github.jeyhung.configs;

import io.github.jeyhung.users.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Autowired
    public WebSecurity(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security").permitAll()
                .antMatchers(HttpMethod.POST, "/users/sign-up").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new AuthFilter(authenticationManager(), userService))
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
