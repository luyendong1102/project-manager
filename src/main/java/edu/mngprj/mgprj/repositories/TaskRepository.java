package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.Task;
import edu.mngprj.mgprj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.project.id = ?1")
    public List<Task> findTaskByProjectID(Long id);

    @Query(value = "select t from Task t where t.name like concat('%',?1,'%') or t.id like concat('%',?1,'%') and ( (t.project.user = ?2) or (?3 in t.users) )")
    public List<Task> findTask(String input, User owner, User worker);

    @Modifying
    @Query(value = "update Task t set t.name = ?1, t.description = ?2, t.deadline = ?3 where t.id = ?4")
    public void updateTask(String name, String description, Date status, Long id);

}
