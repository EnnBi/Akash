package com.akash.entity;

import com.akash.entity.AppUser;
import com.akash.entity.Manufacture;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="labour_info")
public class LabourInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="quantity")
    private Double quantity;
    @Column(name="amount_per_head")
    private Double amountPerHead;
    @Column(name="total_amount")
    private Double totalAmount;
    @ManyToOne
    Manufacture manufacture;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name="Labourinfo_Labours")
    List<AppUser> labours;
    @Transient
    private Long sizeId;
    @Transient
    private Double cement;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmountPerHead() {
        return this.amountPerHead;
    }

    public void setAmountPerHead(Double amountPerHead) {
        this.amountPerHead = amountPerHead;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<AppUser> getLabours() {
        return this.labours;
    }

    public void setLabours(List<AppUser> labours) {
        this.labours = labours;
    }

    public Manufacture getManufacture() {
        return this.manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public Long getSizeId() {
        return this.sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Double getCement() {
        return this.cement;
    }

    public void setCement(Double cement) {
        this.cement = cement;
    }

    public String toString() {
        return "LabourInfo [id=" + this.id + ", quantity=" + this.quantity + ", amountPerHead=" + this.amountPerHead + ", totalAmount=" + this.totalAmount + ", labours=" + this.labours + "]";
    }
}
