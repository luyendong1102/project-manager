package edu.mngprj.mgprj.repositories;

import edu.mngprj.mgprj.entities.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetail, Long> {
}
