package otmankarim.Capstone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.UnauthorizedException;
import otmankarim.Capstone.services.UserSRV;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserSRV userSRV;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please put the authorization token in header");

        String accessToken = authHeader.substring(7);
        System.out.println(accessToken);

        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        User user = userSRV.getUserById(UUID.fromString(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match("/auth/**", request.getServletPath())
                || antPathMatcher.match("/v3/api-docs/**", request.getServletPath())
                || antPathMatcher.match("/swagger-ui/**", request.getServletPath())
                || antPathMatcher.match("/users", request.getServletPath());

    }
}
