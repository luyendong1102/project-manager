package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.UserLogin;
import edu.mngprj.mgprj.entities.UserModded;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserSercurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserLogin> userLogin = userLoginRepository.findByUserName(username);
        if (userLogin.isEmpty()) {
            throw new UsernameNotFoundException("username : " + username + " not found");
        }
        return new UserModded(userLogin.get().getUser());
    }
}
