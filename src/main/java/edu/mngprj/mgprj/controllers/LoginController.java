package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.entities.UserLogin;
import edu.mngprj.mgprj.services.AuthencationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthencationService authencationService;

    @PostMapping()
    public ResponseEntity<ResponseTemplate> auth(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Logged in", authencationService.login(userLogin)
                )
        );
    }

}
