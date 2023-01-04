package co.com.jorge.springboot.app.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 707677639507221145L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "descripcion")
    private String description;

    @Column(name = "observacion")
    private String observation;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "factura_id")
    private List<ItemInvoice> itemInvoices;

    public Invoice() {
        this.itemInvoices = new ArrayList<ItemInvoice>();
    }

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ItemInvoice> getItemInvoices() {
        return itemInvoices;
    }

    public void setItemInvoices(List<ItemInvoice> itemInvoices) {
        this.itemInvoices = itemInvoices;
    }

    public void addItemInvoice(ItemInvoice itemInvoice){
        itemInvoices.add(itemInvoice);
    }

    public Double getTotal(){
        return itemInvoices.stream().map(ItemInvoice::calculatePrice).reduce(Double::sum).orElse(null);
    }
}
