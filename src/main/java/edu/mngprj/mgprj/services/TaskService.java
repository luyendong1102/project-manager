package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.Project;
import edu.mngprj.mgprj.entities.Task;
import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.entities.UserModded;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.repositories.ProjectRrepository;
import edu.mngprj.mgprj.repositories.TaskRepository;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private ProjectRrepository projectRrepository;

    private User getCurrentUser() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        return user.get();
    }

    @Transactional
    public Task createNewTask(Task task) throws NotValidUserException {
        if (getCurrentUser().getProjects().stream()
                .noneMatch(
                        project -> {
                            return project.getId().equals(task.getProject().getId());
                        }
                )) {
            throw new NotValidUserException();
        }
        return taskRepository.save(task);
    }


    // TODO : CANNOT DELETE
    @Transactional
    public void deleteTask(Long id) throws NotValidUserException, NotFoundException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        if (getCurrentUser().getProjects().stream()
                .noneMatch(
                        project -> {
                            return project.getId().equals(task.get().getProject().getId());
                        }
                )) {
            throw new NotValidUserException();
        }
        List<User> users = task.get().getUsers();
        users.forEach(
                u -> {
                    u.getTasks().remove(task.get());
                }
        );
        taskRepository.deleteById(task.get().getId());
    }

    @Transactional
    public Task updateTask(Task updateTask) throws NotFoundException, NotValidUserException {
        Optional<Task> task = taskRepository.findById(updateTask.getId());
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
//        if (getCurrentUser().getProjects().stream()
//                .noneMatch(
//                        project -> {
//                            return project.getId().equals(task.get().getProject().getId());
//                        }
//                )) {
//            throw new NotValidUserException();
//        }
        return taskRepository.save(updateTask);
    }

    public List<Task> getAllTaskInProject(Long id) throws NotValidUserException, NotFoundException {
        if (getCurrentUser().getProjects().stream()
                .noneMatch(
                        project -> {
                            return project.getId().equals(id);
                        }
                )) {
            throw new NotValidUserException();
        }
        Optional<Project> project = projectRrepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException();
        }
        return taskRepository.findTaskByProjectID(id);
    }

    public Set<Task> getUserTask() {
        return getCurrentUser().getTasks();
    }

    public Task getTask(Long id) throws NotValidUserException, NotFoundException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        User user = getCurrentUser();

        boolean owner = user.getProjects().stream().anyMatch(
                project -> {
                    return project.getId().equals(task.get().getProject().getId());
                }
        );

        boolean worker = user.getTasks().stream().anyMatch(
                taskk -> {
                    return taskk.equals(task.get().getId());
                }
        );

        if (owner || worker) {
            return task.get();
        } else {
            throw new NotValidUserException();
        }
    }

    // TODO : CANNOT FIND
    public List<Task> findTask(String input) {
        User user = getCurrentUser();
        return taskRepository.findTask(input, user, user);
    }

    @Transactional
    public Task addUserToTask(Long id, User user) throws NotFoundException, NotValidUserException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        if (getCurrentUser().getProjects().stream()
                .noneMatch(
                        project -> {
                            return project.getId().equals(task.get().getProject().getId());
                        }
                )) {
            throw new NotValidUserException();
        }
        if (task.get().getUsers().stream().anyMatch(
                user1 -> {
                    return user1.getId().equals(user.getId());
                }
        )) {
            throw new NotFoundException();
        }
        User u = userRepository.findById(user.getId()).get();
        u.getTasks().add(task.get());
        task.get().getUsers().add(u);
        return taskRepository.save(task.get());
    }

    @Transactional
    public Task removeUser(Long id, User user) throws NotFoundException, NotValidUserException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NotFoundException();
        }
        if (getCurrentUser().getProjects().stream()
                .noneMatch(
                        project -> {
                            return project.getId().equals(task.get().getProject().getId());
                        }
                )) {
            throw new NotValidUserException();
        }
        if (task.get().getUsers().stream().noneMatch(
                user1 -> {
                    return user1.getId().equals(user.getId());
                }
        )) {
            throw new NotFoundException();
        }
        User u = userRepository.findById(user.getId()).get();
        u.getTasks().remove(task.get());
        task.get().getUsers().remove(u);
        return taskRepository.save(task.get());
    }

}
