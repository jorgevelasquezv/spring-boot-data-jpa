package co.com.jorge.springboot.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    public String locale(HttpServletRequest request){
        String ultimateUrl = request.getHeader("referer");
        return "redirect:".concat(ultimateUrl);
    }
}
