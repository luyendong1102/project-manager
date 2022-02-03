package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.entities.Project;
import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.entities.UserModded;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.repositories.ProjectRrepository;
import edu.mngprj.mgprj.repositories.UserLoginRepository;
import edu.mngprj.mgprj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private ProjectRrepository projectRrepository;

    @Transactional
    public Project createNewProject(Project project) {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        project.setUser(user.get());
        return projectRrepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id) throws NotFoundException {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        Optional<Project> project = projectRrepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException();
        }
        if (!project.get().getUser().getId().equals(user.get().getId())) {
            throw new NotFoundException();
        }
        projectRrepository.deleteById(id);
    }

    public List<Project> getAllProject() {
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        return projectRrepository.findProjectByUser(user.get());
    }

    public List<Project> findProject(String input) {
        return projectRrepository.findByString(input);
    }

    public Project getProjectByID(Long id) throws NotFoundException, NotValidUserException {
        Optional<Project> p = projectRrepository.findById(id);
        UserModded userDetails = (UserModded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUserLogin(userLoginRepository.findByUserName(userDetails.getUsername()).get());
        if (!p.get().getUser().getId().equals(user.get().getId())) {
            throw new NotValidUserException();
        }
        if (p.isEmpty()) {
            throw new NotFoundException();
        }
        return p.get();
    }



}
