// package back_end.e_commerce.security;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import back_end.e_commerce.service.JWTService;
// import back_end.e_commerce.dao.LocalUserDAO;
// import back_end.e_commerce.model.LocalUser;
// import com.auth0.jwt.exceptions.JWTDecodeException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import java.io.IOException;
// import java.util.Optional;
// import java.util.ArrayList;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;    
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// @Component
// public class JWTRequestFilter extends OncePerRequestFilter {

//     private JWTService jwtService;
//     private LocalUserDAO localUserDAO;

//     public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
//         this.jwtService = jwtService;
//         this.localUserDAO = localUserDAO;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

//         String tokenHeader = request.getHeader("Authorization");
//         if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//             String token = tokenHeader.substring(7);
//             try {
//                 String username = jwtService.getUsername(token);
//                 Optional<LocalUser> userOpt = localUserDAO.findByUsernameIgnoreCase(username);
//                 if (userOpt.isPresent()) {
//                     LocalUser user = userOpt.get();
//                     UsernamePasswordAuthenticationToken authentication =
//                             new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//                     SecurityContextHolder.getContext().setAuthentication(authentication);
//                 }
//             } catch (JWTDecodeException ex) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 return;
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }
package back_end.e_commerce.security;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import back_end.e_commerce.service.JWTService;
import back_end.e_commerce.dao.LocalUserDAO;
import back_end.e_commerce.model.LocalUser;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;    
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserDAO localUserDAO;

    Long userId = jwtService.getUserId(token);

Optional<LocalUser> userOpt = localUserDAO.findById(userId);
    public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> userOpt = localUserDAO.findByUsernameIgnoreCase(username);
                if (userOpt.isPresent()) {
                    LocalUser user = userOpt.get();
                   UsernamePasswordAuthenticationToken authentication =
    new UsernamePasswordAuthenticationToken(
        user,
        null,
        List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}