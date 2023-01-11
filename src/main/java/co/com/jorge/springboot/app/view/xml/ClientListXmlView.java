package co.com.jorge.springboot.app.view.xml;

import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Map;

@Component("list.xml")
public class ClientListXmlView extends MarshallingView {

    @Autowired
    public ClientListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.remove("titulo");
        model.remove("page");
        Page<Client> clients = (Page<Client>) model.get("clientes");

        model.remove("clientes");

        model.put("clientList", new ClienteList(clients.getContent()));

        super.renderMergedOutputModel(model, request, response);
    }
}
