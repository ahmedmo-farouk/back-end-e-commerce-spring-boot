package back_end.e_commerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import back_end.e_commerce.service.JWTService;
import back_end.e_commerce.model.LocalUser;
import back_end.e_commerce.dao.LocalUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final LocalUserRepository userRepository;

    public JWTRequestFilter(JWTService jwtService, LocalUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Processing request: " + request.getRequestURI() + " with auth header: " + authHeader);

        // إذا مفيش JWT → سيب الريكويست يكمل
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            Long userId = jwtService.getUserId(token);
            System.out.println("Extracted userId from JWT: " + userId);

            if (userId != null) {
                LocalUser user = userRepository.findById(userId).orElse(null);
                System.out.println("User found in database: " + (user != null ? user.getId() : "null"));

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Authentication set for user: " + user.getId());
                }
            }

        } catch (Exception e) {
            System.err.println("JWT validation error: " + e.getMessage());
            e.printStackTrace();
            // ignore and continue → user will be unauthenticated
        }

        filterChain.doFilter(request, response);
    }
}
