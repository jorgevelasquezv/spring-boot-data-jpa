package co.com.jorge.springboot.app.view.xml;

import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "clients")
public class ClienteList {

    @XmlElement(name = "client")
    private List<Client> clients;

    public ClienteList() {
    }

    public ClienteList(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

}
