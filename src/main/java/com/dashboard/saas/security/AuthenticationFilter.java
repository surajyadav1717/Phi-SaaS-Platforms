package com.dashboard.saas.security;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.entities.principle.UserDetailsPrinciple;
import com.dashboard.saas.entities.principle.UserPrincipal;
import com.dashboard.saas.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            boolean valid = jwtTokenProvider.validateToken(token);

            try {


                if (valid) {

                    long userId = jwtTokenProvider.userIdFromToken(token);

                    Optional<Users> userOptional = userRepository.findById(userId);

                    if (userOptional.isPresent()) {
                        Users user = userOptional.get();

//                        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail(), user.getName());

                        UserPrincipal userPrincipal = new UserPrincipal();
                        userPrincipal.setUserId((user.getId()));
                        userPrincipal.setEmail(user.getEmail());
                        userPrincipal.setFullName(user.getName());

                        UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple(userPrincipal);

                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetailsPrinciple,
                                        null,
                                        userDetailsPrinciple.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                        SecurityContextHolder.getContext()
                                .setAuthentication(authenticationToken);


                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        filterChain.doFilter(request, response);

    }
}
