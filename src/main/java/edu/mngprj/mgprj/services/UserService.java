package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.*;
import edu.mngprj.mgprj.exceptions.PasswordNotMatchException;
import edu.mngprj.mgprj.repositories.ProjectRrepository;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectRrepository projectRrepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User register(User user) {
        Set<UserRole> roles = new HashSet<>();
        // set default roles
        roles.add(new UserRole(4L, "ROLE_NHANVIEN", null));
        user.setRoles(roles);
        user.set_enable(true);
        // set up account
        user.getUserLogin().setUser(user);
        user.getUserLogin().setPassword(passwordEncoder.encode(user.getUserLogin().getPassword()));
        return userRepository.save(user);
    }

    public User getYourSelf() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        return user.get();
    }

    public Set<UserRole> getRoles() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        return user.get().getRoles();
    }

    public boolean upgradeAccount(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User uuser = user.get();
        if (uuser.getRoles().contains(new UserRole(3L, "ROLE_QUANLY", null))) {
            return false;
        }
        uuser.getRoles().add(new UserRole(3L, "ROLE_QUANLY", null));
        userRepository.save(uuser);
        return true;
    }

    @Transactional
    public User changePassword(PasswordChanger pc) throws PasswordNotMatchException {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        String newhash = passwordEncoder.encode(pc.getNewpassword());

        if (!passwordEncoder.matches(pc.getOldpassword(), userDetails.getPassword())) {
            throw new PasswordNotMatchException();
        }

        UserLogin ul = userLoginRepository.findByUser(user.get());
        ul.setPassword(newhash);
        userLoginRepository.save(ul);
        return ul.getUser();
    }

    @Transactional
    public User disableAccount() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        User uuser = user.get();
        uuser.set_enable(false);
        return userRepository.save(uuser);
    }

    public List<User> findUser(String input) {
        return userRepository.findUserByString(input);
    }
    
}
