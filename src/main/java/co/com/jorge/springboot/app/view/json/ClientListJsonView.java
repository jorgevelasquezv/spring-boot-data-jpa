package co.com.jorge.springboot.app.view.json;

import co.com.jorge.springboot.app.models.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("list.json")
public class ClientListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {

        model.remove("titulo");
        model.remove("page");

        Page<Client> clients = (Page<Client>) model.get("clientes");

        model.remove("clientes");
        model.put("clientes", clients.getContent());

        return super.filterModel(model);
    }
}
