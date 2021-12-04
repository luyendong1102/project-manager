package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    @Query("select u from UserLogin u where u.username = ?1")
    public Optional<UserLogin> findByUserName(String username);

    @Query("select ul from UserLogin ul where ul.user = ?1")
    public UserLogin findByUser(User user);

}
