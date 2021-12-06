package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.Project;
import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@Secured("ROLE_QUANLY")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<ResponseTemplate> getAllProject() {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Your project(s)", projectService.getAllProject())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> getProjectByID(@PathVariable Long id) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Result", projectService.getProjectByID(id))
        );
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseTemplate> findProject(@RequestParam String p) {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Result", projectService.findProject(p))
        );
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Project " + project.getName() + " created", projectService.createNewProject(project))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteProject(@PathVariable Long id) throws NotFoundException {
        projectService.deleteProject(id);
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Prject has id : " + id + " deleted", null)
        );
    }

}
