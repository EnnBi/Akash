package com.akash.entity;

import com.akash.entity.AppUser;
import com.akash.entity.MaterialType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="raw_material")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @NotNull(message="select any material")
    @ManyToOne
    @JoinColumn(name="material_id")
    MaterialType material;
    @NotNull(message="select any app user")
    @ManyToOne
    @JoinColumn(name="dealer")
    AppUser dealer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date")
    LocalDate date;
    @NotEmpty(message="chalan number is required")
    @NotNull(message="chalan number is required")
    @Column(name="chalan_number")
    private String chalanNumber;
    @NotNull(message="quantity is required")
    @Column(name="quantity")
    private Double quantity = 0.0;
    @NotNull(message="amount is required")
    @Column(name="amount")
    private Double amount = 0.0;
    @NotEmpty(message="unit is required")
    @NotNull(message="unit is required")
    @Column(name="unit")
    private String unit;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MaterialType getMaterial() {
        return this.material;
    }

    public void setMaterial(MaterialType material) {
        this.material = material;
    }

    public AppUser getDealer() {
        return this.dealer;
    }

    public void setDealer(AppUser dealer) {
        this.dealer = dealer;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getChalanNumber() {
        return this.chalanNumber;
    }

    public void setChalanNumber(String chalanNumber) {
        this.chalanNumber = chalanNumber;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String toString() {
        return "RawMaterial [id=" + this.id + ", material=" + this.material + ", dealer=" + this.dealer + ", date=" + this.date + ", chalanNumber=" + this.chalanNumber + ", quantity=" + this.quantity + ", amount=" + this.amount + ", unit=" + this.unit + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (int)(this.id ^ this.id >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        RawMaterial other = (RawMaterial)obj;
        return this.id == other.id;
    }
}
