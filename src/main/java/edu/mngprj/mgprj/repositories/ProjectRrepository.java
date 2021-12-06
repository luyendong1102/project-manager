package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.Project;
import edu.mngprj.mgprj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRrepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p where p.name like concat('%',?1,'%') or p.id like concat('%',?1,'%')")
    List<Project> findByString(String input);

    @Query("select p from Project p where p.user = ?1")
    List<Project> findProjectByUser(User user);
}
