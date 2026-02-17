package com.akash.entity;

public class InventoryCount {
    String name;
    String size;
    Double manufactured;
    Double sold;
    Double remaining;

    public InventoryCount(String name, String size, Double manufactured, Double sold) {
        this.name = name;
        this.size = size;
        this.manufactured = manufactured == null ? Double.valueOf(0.0) : manufactured;
        this.sold = sold == null ? Double.valueOf(0.0) : sold;
        this.remaining = this.manufactured - this.sold;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getManufactured() {
        return this.manufactured;
    }

    public void setManufactured(Double manufactured) {
        this.manufactured = manufactured;
    }

    public Double getSold() {
        return this.sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }

    public Double getRemaining() {
        return this.remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
}
