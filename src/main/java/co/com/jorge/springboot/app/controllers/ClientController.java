package co.com.jorge.springboot.app.controllers;

import co.com.jorge.springboot.app.models.entities.Client;
import co.com.jorge.springboot.app.models.service.IClientService;
import co.com.jorge.springboot.app.models.service.IUploadFileService;
import co.com.jorge.springboot.app.util.paginator.PageRender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;


@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private IUploadFileService uploadFileService;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MessageSource messageSource;

    @Secured("ROLE_USER")
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

    @Secured("ROLE_USER")
    @GetMapping("/see/{id}")
    public String see(@PathVariable("id") Long id, Model model, RedirectAttributes flash, Locale locale) {
//        Client client = clientService.findById(id);
        Client client = clientService.fetchByIdWithInvoice(id);
        if (client == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.flash.see.error.id", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("titulo",
                messageSource.getMessage("text.flash.see.titulo.form.detail", null, locale)
                + client.getName() + " " + client.getLastname());
        return "see";
    }


    @GetMapping({"/list", "/"})
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          Model model, Authentication authentication,
                          HttpServletRequest request, Locale locale) {

        if (authentication != null){
            logger.info("Usuario autenticado correctamente, username: ".concat(authentication.getName()));
        }

//        Forma estática de obtener el authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            logger.info("Forma estática. Usuario autenticado correctamente, username: ".concat(auth.getName()));
        }

        if (hasRole("ROLE_ADMIN")){
            logger.info("Usuario autenticado: ".concat(auth.getName()).concat(" tiene acceso"));
        }else {
            logger.info("Usuario autenticado: ".concat(auth.getName()).concat(" no tiene acceso"));
        }

//      Validar usuario otra forma con SecurityContextHolderAwareRequestWrapper
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");

        if (securityContext.isUserInRole("ADMIN")){
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper. Usuario autenticado: ".concat(auth.getName()).concat(" tiene acceso"));
        }else {
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper. Usuario autenticado: ".concat(auth.getName()).concat(" NO tiene acceso"));
        }

//      Validar usuario otra forma con HttpServletRequest
        if (request.isUserInRole("ROLE_ADMIN")){
            logger.info("Forma usando HttpServletRequest. Usuario autenticado: ".concat(auth.getName()).concat(" tiene acceso"));
        }else {
            logger.info("Forma usando HttpServletRequest. Usuario autenticado: ".concat(auth.getName()).concat(" NO tiene acceso"));
        }

        Pageable pageRequest = PageRequest.of(page, 4);

        Page<Client> clients = clientService.findAll(pageRequest);

        PageRender<Client> pageRender = new PageRender<>("/list", clients);

        model.addAttribute("titulo", messageSource.getMessage("text.cliente.lista.titulo", null, locale));
        model.addAttribute("clientes", clients);
        model.addAttribute("page", pageRender);

        return "list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String create(Model model, Locale locale) {

        Client client = new Client();
        model.addAttribute("client", client);
        model.addAttribute("titulo", messageSource.getMessage("text.flash.see.titulo.form", null, locale));

        return "form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String save(@Valid Client client, BindingResult result, Model model,
                       @RequestParam("file") MultipartFile photo, RedirectAttributes flash,
                       SessionStatus status, Locale locale) {

        if (result.hasFieldErrors()) {
            model.addAttribute("titulo", messageSource.getMessage("text.flash.see.titulo.form", null, locale));
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
                    messageSource.getMessage("text.flash.see.info.image", null, locale) + uniqueFilename + "'");
            client.setPhoto(uniqueFilename);

        }
        String mensajeFlash = (client.getId() != null)
                ? messageSource.getMessage("text.flash.see.success.edit.client", null, locale)
                : messageSource.getMessage("text.flash.see.success.create.client", null, locale);
        clientService.save(client);

        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);

        return "redirect:list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes flash, Locale locale) {

        Client client = null;

        if (id > 0) {
            client = clientService.findById(id);
            if (client == null) {
                flash.addFlashAttribute("error", messageSource.getMessage("text.flash.see.error.client.id.null", null, locale));
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", messageSource.getMessage("text.flash.see.error.client.id.zero", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("client", client);
        model.addAttribute("titulo", messageSource.getMessage("text.flash.see.titulo.form.edit", null, locale));

        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash, Locale locale) {

        if (id > 0) {
            Client client = clientService.findById(id);
            clientService.delete(id);
            flash.addFlashAttribute("success",
                    messageSource.getMessage("text.flash.see.success.delete.client", null, locale));

            if (uploadFileService.delete(client.getPhoto())) {
                flash.addFlashAttribute("info",
                        messageSource.getMessage("text.flash.see.success.delete.photo", null, locale)
                                + client.getPhoto());
            }
        }
        return "redirect:/list";
    }

    private boolean hasRole(String role){

        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null){
            return false;
        }

        Authentication authentication = context.getAuthentication();

        if (authentication == null){
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));

    }
}
