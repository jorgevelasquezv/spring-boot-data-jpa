package co.com.jorge.springboot.app.controllers;

import co.com.jorge.springboot.app.models.service.IClientService;
import co.com.jorge.springboot.app.view.xml.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    public ClienteList listAll() {

        return new ClienteList(clientService.findAll());
    }
}
