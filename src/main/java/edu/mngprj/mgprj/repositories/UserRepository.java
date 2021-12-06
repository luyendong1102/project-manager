package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userLogin = ?1")
    public Optional<User> findByUserLogin(UserLogin userLogin);

    @Query("select u from User u where u.name like concat('%',?1,'%') or u.phone like concat('%',?1,'%') or u.id like concat('%',?1,'%')")
    public List<User> findUserByString(String input);
    
}
