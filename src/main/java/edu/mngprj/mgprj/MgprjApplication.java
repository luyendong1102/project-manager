package edu.mngprj.mgprj;

import edu.mngprj.mgprj.repositories.UserRepository;
import edu.mngprj.mgprj.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MgprjApplication implements CommandLineRunner {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MgprjApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        // create role
//        UserRole admin_test = new UserRole();
//        admin_test.setId(1L);
//        admin_test.setRole_name("ROLE_ADMIN_TESTER");
//        userRoleRepository.save(admin_test);
//        UserRole admin = new UserRole();
//
//        admin.setId(2L);
//        admin.setRole_name("ROLE_ADMIN");
//        userRoleRepository.save(admin);
//        UserRole quanly = new UserRole();
//
//        quanly.setId(3L);
//        quanly.setRole_name("ROLE_QUANLY");
//        userRoleRepository.save(quanly);
//        UserRole nhanvien = new UserRole();
//
//        nhanvien.setId(4L);
//        nhanvien.setRole_name("ROLE_NHANVIEN");
//        userRoleRepository.save(nhanvien);
//
//        // add default account
//        User user = new User();
//        user.setId(1L);
//        user.set_enable(true);
//        user.setName("ASS MINN");
//        user.setPhone("0123456789");
//        user.setResume("Verry bad at school");
//        user.setBirthdate(Date.valueOf("1999-03-01"));
//
//        user.setRoles(new HashSet<>());
//        user.getRoles().add(admin);
//        user.getRoles().add(admin_test);
//        user.getRoles().add(quanly);
//        user.getRoles().add(nhanvien);
//
//        UserLogin userLogin = new UserLogin();
//        userLogin.setUser(user);
//        userLogin.setUsername("admin");
//        userLogin.setPassword(passwordEncoder.encode("1"));
//
//        user.setUserLogin(userLogin);
//
//        userRepository.save(user);
    }
}
