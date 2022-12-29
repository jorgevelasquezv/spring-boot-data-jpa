package co.com.jorge.springboot.app.controllers;

import co.com.jorge.springboot.app.models.entities.Client;
import co.com.jorge.springboot.app.models.service.IClientService;
import co.com.jorge.springboot.app.models.service.IUploadFileService;
import co.com.jorge.springboot.app.util.paginator.PageRender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;


@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> seePhoto(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }


    @GetMapping("/see/{id}")
    public String see(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        Client client = clientService.findById(id);
        if (client == null) {
            flash.addFlashAttribute("error", "EL cliente no existe en la base de datos");
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("titulo", "Detalle cliente: "
                + client.getName() + " " + client.getLastname());
        return "see";
    }


    @GetMapping("/list")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest = PageRequest.of(page, 4);

        Page<Client> clients = clientService.findAll(pageRequest);

        PageRender<Client> pageRender = new PageRender<>("/list", clients);

        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clients);
        model.addAttribute("page", pageRender);

        return "list";
    }

    @GetMapping("/form")
    public String create(Model model) {

        Client client = new Client();
        model.addAttribute("client", client);
        model.addAttribute("titulo", "Formulario de Cliente");

        return "form";
    }

    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus status) {

        if (result.hasFieldErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        if (!photo.isEmpty()) {

            if (client.getId() != null && client.getId() > 0 && client.getPhoto() != null && client.getPhoto().length() > 0) {
                uploadFileService.delete(client.getPhoto());
            }

            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            flash.addFlashAttribute("info",
                    "Ha cargado correctamente la imagen '" + uniqueFilename + "'");
            client.setPhoto(uniqueFilename);

        }
        String mensajeFlash = (client.getId() != null) ? "Cliente editado con éxito" : "Cliente creado con éxito";
        clientService.save(client);

        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);

        return "redirect:list";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes flash) {

        Client client = null;

        if (id > 0) {
            client = clientService.findById(id);
            if (client == null) {
                flash.addFlashAttribute("error", "El id del cliente no existe en la BBDD");
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", "El id del cliente no puede ser 0");
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("titulo", "Editar Cliente");

        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {

        if (id > 0) {
            Client client = clientService.findById(id);
            clientService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");

            if (uploadFileService.delete(client.getPhoto())) {
                flash.addFlashAttribute("info", "Foto " + client.getPhoto() + " eliminada con éxito");
            }
        }
        return "redirect:/list";
    }
}
