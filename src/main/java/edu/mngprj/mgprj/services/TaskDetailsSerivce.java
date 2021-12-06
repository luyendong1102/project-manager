package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.Task;
import edu.mngprj.mgprj.entities.TaskDetail;
import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.entities.UserModded;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.repositories.TaskDetailsRepository;
import edu.mngprj.mgprj.repositories.TaskRepository;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDetailsSerivce {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskDetailsRepository taskDetailsRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private TaskRepository taskRepository;

    private User getCurrentUser() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        return user.get();
    }

    @Transactional
    public TaskDetail createCommit(Long taskID, TaskDetail taskDetail) throws NotFoundException, NotValidUserException {
        Optional<Task> task = taskRepository.findById(taskID);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        User u = getCurrentUser();
        if (
                u.getTasks().stream().noneMatch(
                        task1 -> {
                            return task1.getId().equals(taskID);
                        }
                )
        ) {
            throw new NotValidUserException();
        }
        taskDetail.setTime(new Date());
        taskDetail.setTask(task.get());
        taskDetail.setUser(u);
        return taskDetailsRepository.save(taskDetail);
    }

    public List<TaskDetail> getAllYourCommitByTaskID(Long taskid) throws NotFoundException, NotValidUserException {
        Optional<Task> task = taskRepository.findById(taskid);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        User u = getCurrentUser();
        if (
                u.getTasks().stream().noneMatch(
                        task1 -> {
                            return task1.getId().equals(taskid);
                        }
                )
        ) {
            throw new NotValidUserException();
        }
        return taskDetailsRepository.findTdByUser(u, task.get());
    }

    public List<TaskDetail> getCommitByTaskID(Long taskid) throws NotFoundException {
        Optional<Task> task = taskRepository.findById(taskid);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        return taskDetailsRepository.findByTask(task.get());
    }

}
