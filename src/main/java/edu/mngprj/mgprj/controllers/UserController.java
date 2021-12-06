package edu.mngprj.mgprj.controllers;

import edu.mngprj.mgprj.entities.PasswordChanger;
import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.entities.User;
import edu.mngprj.mgprj.exceptions.PasswordNotMatchException;
import edu.mngprj.mgprj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured("ROLE_QUANLY")
    @GetMapping("/all")
    public ResponseEntity<ResponseTemplate> getAllUser() {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "List user", userService.findAllUser()
                )
        );
    }

    @Secured("ROLE_QUANLY")
    @GetMapping("/upgrade/{id}")
    public ResponseEntity<ResponseTemplate> upgradeUser(@PathVariable Long id) {
        boolean tester = userService.upgradeAccount(id);
        if (tester) {
            return ResponseEntity.ok(
                    new ResponseTemplate(200, "Account has ID : " + id + " has been upgraded", null)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseTemplate(400, "Error", null)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseTemplate> register(@RequestBody User user) {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Account : " + user.getName() + " registered", userService.register(user)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ResponseTemplate> getSelf() {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Your information", userService.getYourSelf()
                )
        );
    }

    @PostMapping("/changepassword")
    public ResponseEntity<ResponseTemplate> changepassword(@RequestBody PasswordChanger pc) throws PasswordNotMatchException {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Password changed", userService.changePassword(pc)
                )
        );
    }

    // self disable
    @GetMapping("/disable")
    public ResponseEntity<ResponseTemplate> selfdisable() {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Your account has been disabled", userService.disableAccount()
                )
        );
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseTemplate> findUser(@RequestParam String p) {
        return ResponseEntity.ok(
                new ResponseTemplate(
                        200, "Your result", userService.findUser(p)
                )
        );
    }

}
