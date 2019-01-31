package org.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Bartek on 7/18/2017.
 */

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("someAttribute", "someValue");
        return "index";
    }
}