package com.akash.entity;

import com.akash.entity.LabourGroup;
import com.akash.entity.LabourInfo;
import com.akash.entity.Product;
import com.akash.entity.Size;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="manufacture")
public class Manufacture {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @ManyToOne
    @JoinColumn(name="product")
    Product product;
    @ManyToOne
    @JoinColumn(name="size")
    Size size;
    @Column(name="date")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate date;
    @Column(name="total_quantity")
    private Double totalQuantity;
    @Column(name="total_amount")
    private Double totalAmount;
    @Column(name="Cost_Per_Unit")
    private Double cpu;
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="labour_id")
    List<LabourInfo> labourInfo;
    @Column(name="cement")
    private Double cement;
    @ManyToOne
    @JoinColumn(name="Labour_Group")
    LabourGroup labourGroup;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalQuantity() {
        return this.totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<LabourInfo> getLabourInfo() {
        return this.labourInfo;
    }

    public void setLabourInfo(List<LabourInfo> labourInfo) {
        this.labourInfo = labourInfo;
    }

    public Double getCement() {
        return this.cement;
    }

    public void setCement(Double cement) {
        this.cement = cement;
    }

    public Double getCpu() {
        return this.cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public LabourGroup getLabourGroup() {
        return this.labourGroup;
    }

    public void setLabourGroup(LabourGroup labourGroup) {
        this.labourGroup = labourGroup;
    }

    public String toString() {
        return "Manufacture [id=" + this.id + ", product=" + this.product + ", size=" + this.size + ", date=" + this.date + ", totalQuantity=" + this.totalQuantity + ", totalAmount=" + this.totalAmount + ", labourInfo=" + this.labourInfo + ", cement=" + this.cement + "]";
    }
}
