package org.example.payroll_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
    @GetMapping("/")
    public String index() {
        return "login";
    }
    @GetMapping("/index")
    public String index2() {
        return "index";
    }
}
