package com.akash.entity;

import com.akash.entity.LabourGroup;
import com.akash.entity.Product;
import com.akash.entity.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LabourCost {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    @Column(name="Rate")
    Double rate;
    @ManyToOne
    @JoinColumn(name="Labour_Group")
    LabourGroup labourGroup;
    @ManyToOne
    @JoinColumn(name="Product")
    Product product;
    @ManyToOne
    @JoinColumn(name="Size")
    Size size;
    @Column(name="Loading_Rate")
    private Double loadingRate;
    @Column(name="Unloading_Rate")
    private Double unloadingRate;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LabourGroup getLabourGroup() {
        return this.labourGroup;
    }

    public void setLabourGroup(LabourGroup labourGroup) {
        this.labourGroup = labourGroup;
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

    public Double getLoadingRate() {
        return this.loadingRate;
    }

    public void setLoadingRate(Double loadingRate) {
        this.loadingRate = loadingRate;
    }

    public Double getUnloadingRate() {
        return this.unloadingRate;
    }

    public void setUnloadingRate(Double unloadingRate) {
        this.unloadingRate = unloadingRate;
    }
}
