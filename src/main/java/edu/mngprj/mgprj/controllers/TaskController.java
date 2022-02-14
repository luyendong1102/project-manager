package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.entities.Task;
import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Secured("ROLE_QUANLY")
    @PostMapping("/create")
    public ResponseEntity<ResponseTemplate> createNewTask(@RequestBody Task newTask) throws NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Task " + newTask.getName() + " created", taskService.createNewTask(newTask))
        );
    }

    @Secured("ROLE_QUANLY")
    @PutMapping
    public ResponseEntity<ResponseTemplate> updateTask(@RequestBody Task updateTask) throws NotFoundException, NotValidUserException {
        taskService.updateTask(updateTask);
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Updated", "")
        );
    }

    @Secured("ROLE_QUANLY")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteTask(@PathVariable Long id) throws NotFoundException, NotValidUserException {
        taskService.deleteTask(id);
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Deleted", null)
        );
    }

    @Secured("ROLE_QUANLY")
    @GetMapping("/project/{id}")
    public ResponseEntity<ResponseTemplate> getTaskByPrjID(@PathVariable Long id) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Project has ID: " + id + " task : ", taskService.getAllTaskInProject(id))
        );
    }

    @GetMapping("/self")
    public ResponseEntity<ResponseTemplate> getUserTask() {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Your tasks", taskService.getUserTask())
        );
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<ResponseTemplate> getTaskByID(@PathVariable Long id) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Task", taskService.getTask(id))
        );
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseTemplate> findTask(@RequestParam String p) {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Result", taskService.findTask(p))
        );
    }

    @Secured("ROLE_QUANLY")
    @PostMapping("/assign/{id}")
    public ResponseEntity<ResponseTemplate> addUser(@PathVariable Long id, @RequestBody User user) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Added", taskService.addUserToTask(id, user))
        );
    }

    @Secured("ROLE_QUANLY")
    @PostMapping("/remove/{id}")
    public ResponseEntity<ResponseTemplate> remove(@PathVariable Long id, @RequestBody User user) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "removed", taskService.removeUser(id, user))
        );
    }

}
