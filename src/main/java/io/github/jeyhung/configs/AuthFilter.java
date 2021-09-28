package io.github.jeyhung.configs;

import io.github.jeyhung.shared.AuthFailedException;
import io.github.jeyhung.shared.UserContextHolder;
import io.github.jeyhung.users.application.UserService;
import io.github.jeyhung.users.application.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.jeyhung.shared.Constants.AUTHORIZATION;

public class AuthFilter extends BasicAuthenticationFilter {
    private final UserService userService;

    public AuthFilter(AuthenticationManager authManager, UserService userService) {
        super(authManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String apikey = req.getHeader(AUTHORIZATION);

        if (apikey != null) {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationByAPIKey(apikey);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
            return;
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByAPIKey(String apikey) {
        UserDto userDto = userService.findByApikey(apikey)
                .orElseThrow(() -> new AuthFailedException("API Key is not valid!"));
        UserContextHolder.setUserId(userDto.getId());
        return new UsernamePasswordAuthenticationToken(userDto, null, null);
    }
}
