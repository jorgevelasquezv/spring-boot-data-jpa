package co.com.jorge.springboot.app.controllers;

import co.com.jorge.springboot.app.models.entities.Client;
import co.com.jorge.springboot.app.models.entities.Invoice;
import co.com.jorge.springboot.app.models.entities.ItemInvoice;
import co.com.jorge.springboot.app.models.entities.Product;
import co.com.jorge.springboot.app.models.service.IClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IClientService clientService;

    @GetMapping("/see/{id}")
    public String see(@PathVariable Long id, Model model, RedirectAttributes flash){
//        Invoice invoice = clientService.findByInvoiceById(id);
        Invoice invoice = clientService.fetchInvoiceByIdWithClientWithItemInvoiceWithProduct(id);

        if (invoice == null){
            flash.addFlashAttribute("error", "Error: La factura no existe en la base de datos ");
            return "redirect:/list";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("titulo", "Factura: ".concat(invoice.getDescription()));

        return "invoice/see";
    }

    @GetMapping("/form/{clientId}")
    public String create(@PathVariable Long clientId, Model model, RedirectAttributes flash) {

        Client client = clientService.findById(clientId);

        if (client == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/list";
        }

        Invoice invoice = new Invoice();
        invoice.setClient(client);

        model.addAttribute("invoice", invoice);
        model.addAttribute("titulo", "Crear Factura");

        return "invoice/form";
    }

    @GetMapping(value = "/load-products/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
        return clientService.findByName(term);
    }

    @PostMapping("/form")
    public String save(@Valid Invoice invoice,
                       BindingResult result,
                       Model model,
                       @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                       @RequestParam(name = "quantity[]", required = false) Integer[] quantity,
                       RedirectAttributes flash,
                       SessionStatus status) {
        if (result.hasErrors()){
            model.addAttribute("titulo", "Crear Factura");
            return "invoice/form";
        }

        if (itemId == null || itemId.length == 0){
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error", "Error: No se puede crear factura sin items");
            return "invoice/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Product product = clientService.findProductById(itemId[i]);
            ItemInvoice itemInvoice = new ItemInvoice();
            itemInvoice.setQuantity(quantity[i]);
            itemInvoice.setProduct(product);
            invoice.addItemInvoice(itemInvoice);

            log.info("ID: " + itemId[i].toString() + ", cantidad: " + quantity[i].toString());
        }

        clientService.saveInvoice(invoice);

        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con éxito");

        return "redirect:/see/" + invoice.getClient().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash){
        Invoice invoice = clientService.findByInvoiceById(id);
        if (invoice != null){
            clientService.deleteInvoice(id);
            flash.addFlashAttribute("success", "Factura eliminada con éxito");
            return "redirect:/see/" + invoice.getClient().getId() ;
        }
        flash.addFlashAttribute("error", "La factura no existe en la base de datos, no es posible eliminar");
        return "redirect:/list";
    }
}
