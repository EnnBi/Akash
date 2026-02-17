package com.akash.entity;

import com.akash.entity.AppUser;
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

@Entity
@Table(name="vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @NotNull(message="number is required")
    @NotEmpty(message="number is required")
    @Column(name="number")
    private String number;
    @ManyToOne
    @JoinColumn(name="appUser_id")
    @NotNull(message="select any driver")
    AppUser driver;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AppUser getDriver() {
        return this.driver;
    }

    public void setDriver(AppUser driver) {
        this.driver = driver;
    }

    public String toString() {
        return "Vehicle [id=" + this.id + ", number=" + this.number + ", driver=" + this.driver + "]";
    }
}
