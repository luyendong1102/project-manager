package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.ResponseTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/hello")
public class TestController {

    @GetMapping
    public ResponseTemplate helloworld() {
        return new ResponseTemplate(200, "This is test message", "This is test content");
    }

}
