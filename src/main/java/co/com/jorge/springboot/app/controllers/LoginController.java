package co.com.jorge.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash){

        if (principal != null){
            flash.addFlashAttribute("info", "Ya ha iniciado sesión previamente");
            return "redirect:/";
        }

        if (error != null){
            model.addAttribute("error", "Error en el inicio de sesión: Nombre de usuario o contraseña incorrecta, intente nuevamente ");
        }

        if (logout != null){
            model.addAttribute("success", "Sesión cerrada exitosamente");
        }

        return "login";
    }
}
