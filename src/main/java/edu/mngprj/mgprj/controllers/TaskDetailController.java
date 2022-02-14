package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.entities.TaskDetail;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.services.TaskDetailsSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/commit")
public class TaskDetailController {

    @Autowired
    private TaskDetailsSerivce taskDetailsSerivce;

    // tao moi commit
    @PostMapping("/create/{taskid}")
    public ResponseEntity<ResponseTemplate> createCommit(@PathVariable Long taskid, @RequestBody TaskDetail commit) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Commited", taskDetailsSerivce.createCommit(taskid, commit))
        );
    }

    // xem commit cua minh
    @GetMapping("/yourcommit/{taskid}")
    public ResponseEntity<ResponseTemplate> getallcommit(@PathVariable Long taskid) throws NotFoundException, NotValidUserException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "Your commit", taskDetailsSerivce.getAllYourCommitByTaskID(taskid))
        );
    }

    // xem commit task minh quan ly
    @GetMapping("/taskcommit/{taskid}")
    public ResponseEntity<ResponseTemplate> getCommitFromTask(@PathVariable Long taskid) throws NotFoundException {
        return ResponseEntity.ok(
                new ResponseTemplate(200, "All commit from this task", taskDetailsSerivce.getCommitByTaskID(taskid))
        );
    }

}
