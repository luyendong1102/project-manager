package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.Task;
import edu.mngprj.mgprj.entities.TaskDetail;
import edu.mngprj.mgprj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetail, Long> {

    @Query("select td from TaskDetail td where td.user = ?1 and td.task = ?2")
    public List<TaskDetail> findTdByUser(User u, Task t);

    @Query("select td from TaskDetail td where td.task = ?1")
    public List<TaskDetail> findByTask(Task t);

}
