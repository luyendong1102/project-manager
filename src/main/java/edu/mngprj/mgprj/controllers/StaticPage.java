package edu.mngprj.mgprj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class StaticPage {

    @GetMapping("/home")
    public String home() {
        return "homepage";
    }

}
