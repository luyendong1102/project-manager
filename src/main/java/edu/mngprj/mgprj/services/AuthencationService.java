package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.UserLogin;
import edu.mngprj.mgprj.entities.UserModded;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import edu.mngprj.mgprj.ultis.JwtUltis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthencationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUltis jwtUltis;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    public String login(UserLogin user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtUltis.genToken(
                (UserModded) auth.getPrincipal()
        );
    }

}
