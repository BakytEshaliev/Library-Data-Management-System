package net.javaguides.springboot.security.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "login";
    }
}
