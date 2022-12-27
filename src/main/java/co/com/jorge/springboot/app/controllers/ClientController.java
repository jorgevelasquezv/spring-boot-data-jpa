package co.com.jorge.springboot.app.controllers;

import co.com.jorge.springboot.app.models.dao.IClientDao;
import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientDao clientDao;

    @GetMapping("/list")
    public String listAll(Model model){
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientDao.findAll());

        return "list";
    }

    @GetMapping("/form")
    public String create(Model model){

        Client client = new Client();
        model.addAttribute("client", client);
        model.addAttribute("titulo", "Formulario de Cliente");

        return "form";
    }

    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status){

        if (result.hasFieldErrors()){
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        clientDao.save(client);

        status.setComplete();

        return "redirect:list";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model){

        Client client = null;

        if (id > 0){
            client = clientDao.findById(id);
        }else {
            return "list";
        }

        model.addAttribute("client", client);
        model.addAttribute("titulo", "Editar Cliente");

        return "form";
    }

}
