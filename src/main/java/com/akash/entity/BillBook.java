package com.akash.entity;

import com.akash.entity.AppUser;
import com.akash.entity.LabourGroup;
import com.akash.entity.Sales;
import com.akash.entity.Site;
import com.akash.entity.Vehicle;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="bill_book")
public class BillBook {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id")
    private long id;
    @ManyToOne
    @JoinColumn(name="customer_id")
    AppUser customer;
    @ManyToOne
    @JoinColumn(name="site")
    Site site;
    @ManyToOne
    @JoinColumn(name="vehicle")
    Vehicle vehicle;
    @Column(name="reciept_number")
    private String receiptNumber;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Column(name="Date")
    private LocalDate date;
    @Column(name="unit")
    private Double unit;
    @Column(name="loading_amount")
    private Double loadingAmount;
    @Column(name="unloading_amount")
    private Double unloadingAmount;
    @Column(name="total")
    private Double total;
    @Column(name="Discount")
    private Double discount;
    @Column(name="paid")
    private Double paid;
    @Column(name="balance")
    private Double balance;
    @OneToMany(cascade={CascadeType.ALL})
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @JoinColumn(name="Bill_Book")
    List<Sales> sales;
    @NotNull(message="Please select Loaders")
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name="BillBook_Loaders")
    List<AppUser> loaders;
    @NotNull(message="Please select Unloaders")
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(name="BillBook_Unloaders")
    List<AppUser> unloaders;
    @Column(name="carriage")
    private Double carraige;
    @Column(name="Loading_Amount_Per_Head")
    private Double loadingAmountPerHead;
    @Column(name="Unloading_Amount_Per_Head")
    private Double unloadingAmountPerHead;
    @Column(name="Sites")
    String sites;
    @Column(name="Driver_Loading_Charges")
    Double driverLoadingCharges;
    @Column(name="Driver_Unloading_Charges")
    Double driverUnloadingCharges;
    @Column(name="Vehicle_Other")
    String otherVehicle;
    @ManyToOne
    AppUser driver;
    @ManyToOne
    LabourGroup labourGroup;
    @ManyToOne
    LabourGroup unloaderLabourGroup;
    @Transient
    DecimalFormat df = new DecimalFormat("#.##");

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getCustomer() {
        return this.customer;
    }

    public void setCustomer(AppUser customer) {
        this.customer = customer;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getReceiptNumber() {
        return this.receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getUnit() {
        return this.unit;
    }

    public void setUnit(Double unit) {
        this.unit = unit;
    }

    public Double getLoadingAmount() {
        return this.loadingAmount;
    }

    public void setLoadingAmount(Double loadingAmount) {
        this.loadingAmount = Objects.nonNull(loadingAmount) ? Double.valueOf(this.df.format(loadingAmount)) : Double.valueOf(0.0);
    }

    public Double getUnloadingAmount() {
        return this.unloadingAmount;
    }

    public void setUnloadingAmount(Double unloadingAmount) {
        this.unloadingAmount = Objects.nonNull(unloadingAmount) ? Double.valueOf(this.df.format(unloadingAmount)) : Double.valueOf(0.0);
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = Objects.nonNull(total) ? Double.valueOf(this.df.format(total)) : Double.valueOf(0.0);
    }

    public Double getPaid() {
        return this.paid;
    }

    public void setPaid(Double paid) {
        this.paid = Objects.nonNull(paid) ? Double.valueOf(this.df.format(paid)) : Double.valueOf(0.0);
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = Objects.nonNull(balance) ? Double.valueOf(this.df.format(balance)) : Double.valueOf(0.0);
    }

    public List<Sales> getSales() {
        return this.sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }

    public List<AppUser> getLoaders() {
        return this.loaders;
    }

    public void setLoaders(List<AppUser> loaders) {
        this.loaders = loaders;
    }

    public List<AppUser> getUnloaders() {
        return this.unloaders;
    }

    public void setUnloaders(List<AppUser> unloaders) {
        this.unloaders = unloaders;
    }

    public Double getCarraige() {
        return this.carraige;
    }

    public void setCarraige(Double carraige) {
        this.carraige = Objects.nonNull(carraige) ? Double.valueOf(this.df.format(carraige)) : Double.valueOf(0.0);
    }

    public Double getLoadingAmountPerHead() {
        return this.loadingAmountPerHead;
    }

    public void setLoadingAmountPerHead(Double loadingAmountPerHead) {
        this.loadingAmountPerHead = Objects.nonNull(loadingAmountPerHead) ? Double.valueOf(this.df.format(loadingAmountPerHead)) : Double.valueOf(0.0);
    }

    public Double getUnloadingAmountPerHead() {
        return this.unloadingAmountPerHead;
    }

    public void setUnloadingAmountPerHead(Double unloadingAmountPerHead) {
        this.unloadingAmountPerHead = Objects.nonNull(unloadingAmountPerHead) ? Double.valueOf(this.df.format(unloadingAmountPerHead)) : Double.valueOf(0.0);
    }

    public String getSites() {
        return this.sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getOtherVehicle() {
        return this.otherVehicle;
    }

    public void setOtherVehicle(String otherVehicle) {
        this.otherVehicle = otherVehicle;
    }

    public LabourGroup getLabourGroup() {
        return this.labourGroup;
    }

    public void setLabourGroup(LabourGroup labourGroup) {
        this.labourGroup = labourGroup;
    }

    public Double getDriverLoadingCharges() {
        return this.driverLoadingCharges;
    }

    public void setDriverLoadingCharges(Double driverLoadingCharges) {
        this.driverLoadingCharges = Objects.nonNull(driverLoadingCharges) ? Double.valueOf(this.df.format(driverLoadingCharges)) : Double.valueOf(0.0);
    }

    public Double getDriverUnloadingCharges() {
        return this.driverUnloadingCharges;
    }

    public void setDriverUnloadingCharges(Double driverUnloadingCharges) {
        this.driverUnloadingCharges = Objects.nonNull(driverUnloadingCharges) ? Double.valueOf(this.df.format(driverUnloadingCharges)) : Double.valueOf(0.0);
    }

    public AppUser getDriver() {
        return this.driver;
    }

    public void setDriver(AppUser driver) {
        this.driver = driver;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = Objects.nonNull(discount) ? Double.valueOf(this.df.format(discount)) : Double.valueOf(0.0);
    }

    public LabourGroup getUnloaderLabourGroup() {
        return this.unloaderLabourGroup;
    }

    public void setUnloaderLabourGroup(LabourGroup unloaderLabourGroup) {
        this.unloaderLabourGroup = unloaderLabourGroup;
    }

    public String toString() {
        return "BillBook [id=" + this.id + ", customer=" + this.customer + ", site=" + this.site + ", vehicle=" + this.vehicle + ", receiptNumber=" + this.receiptNumber + ", unit=" + this.unit + ", loadingAmount=" + this.loadingAmount + ", unloadingAmount=" + this.unloadingAmount + ", total=" + this.total + ", paid=" + this.paid + ", balance=" + this.balance + ", sales=" + this.sales + ", loaders=" + this.loaders + ", unloaders=" + this.unloaders + ", carriage=" + this.carraige + "]";
    }
}
