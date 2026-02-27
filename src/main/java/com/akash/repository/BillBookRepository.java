package com.akash.repository;

import com.akash.entity.AppUser;
import com.akash.entity.BillBook;
import com.akash.entity.CustomerStatement;
import com.akash.entity.DriverStatement;
import com.akash.entity.LabourStatement;
import com.akash.entity.SalesStatement;
import com.akash.entity.dto.BillBookDTO;
import com.akash.repository.custom.BillBookCustomizedRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BillBookRepository
extends CrudRepository<BillBook, Long>,
BillBookCustomizedRepository {
    public boolean existsByReceiptNumber(String receiptNumber);

    @Query("Select new com.akash.entity.dto.BillBookDTO(b.id, b.receiptNumber, b.customer.name, b.customer.address, b.date, b.sites, b.total) from BillBook b where b.receiptNumber = :receiptNumber ORDER BY b.date DESC")
    public List<BillBookDTO> findDTOsByReceiptNumber(@Param("receiptNumber") String receiptNumber);

    @Query("Select new com.akash.entity.dto.BillBookDTO(b.id, b.receiptNumber, b.customer.name, b.customer.address, b.date, b.sites, b.total) from BillBook b where b.receiptNumber like :term%")
    public List<BillBookDTO> searchDTOsByReceiptNumber(@Param("term") String term);

    @Query(value="Select Sum(b.carraige) from BillBook b where b.driver.id = :id and b.date between :startDate and :endDate")
    public Double sumOfCarraige(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.driverLoadingCharges) from BillBook b where :driver MEMBER OF b.loaders and b.date between :startDate and :endDate")
    public Double sumOfDriverLoading(@Param(value="driver") AppUser driver, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.driverUnloadingCharges) from BillBook b where :driver MEMBER OF b.unloaders and b.date between :startDate and :endDate")
    public Double sumOfDriverUnloading(@Param(value="driver") AppUser driver, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DriverStatement(b.date,b.receiptNumber,b.customer.name,b.sites,b.carraige,b.driverLoadingCharges,b.driverUnloadingCharges,'BillBook')  from BillBook b where (:driver MEMBER OF b.loaders OR :driver MEMBER OF b.unloaders OR b.driver = :driver ) AND (b.date between :startDate and :endDate) ORDER BY b.date ASC")
    public List<DriverStatement> findDriverDebits(@Param(value="driver") AppUser driver, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.loadingAmountPerHead) from BillBook b where :labour MEMBER OF b.loaders and b.date between :startDate and :endDate")
    public Double sumOfLabourLoading(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.unloadingAmountPerHead) from BillBook b where :labour MEMBER OF b.unloaders and b.date between :startDate and :endDate")
    public Double sumOfLabourUnloading(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(b.date,b.loadingAmountPerHead,b.unloadingAmountPerHead,b.receiptNumber,'BillBook') from BillBook b where (:labour Member Of b.loaders and :labour Member Of b.unloaders)  and  (b.date between :startDate and :endDate)")
    public List<LabourStatement> findLabourBillBookDebitsBoth(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(b.date,b.loadingAmountPerHead,b.receiptNumber,'BillBook') from BillBook b where (:labour Member Of b.loaders and :labour Not Member Of b.unloaders)  and  (b.date between :startDate and :endDate)")
    public List<LabourStatement> findLabourBillBookDebitsLoading(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(b.unloadingAmountPerHead,b.date,b.receiptNumber,'BillBook') from BillBook b where (:labour Member Of b.unloaders and :labour Not Member Of b.loaders)  and  (b.date between :startDate and :endDate)")
    public List<LabourStatement> findLabourBillBookDebitsUnloading(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.total) from BillBook b where b.customer.id = :id and b.date between :startDate and :endDate")
    public Double sumOfCustomerDebits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(b.discount) from BillBook b where b.customer.id = :id and b.date between :startDate and :endDate")
    public Double sumOfCustomerDiscounts(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.CustomerStatement(b.id,b.receiptNumber,b.date,b.vehicle,b.otherVehicle,b.customer.address,b.sites,b.loadingAmount,b.unloadingAmount,b.carraige,b.discount,b.total) from BillBook b where b.customer.id = :id and (b.date between :startDate and :endDate)")
    public List<CustomerStatement> findCustomerBillBookDebits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new  com.akash.entity.SalesStatement(s.product.name,s.size.name,s.quantity,s.unitPrice,s.amount) from Sales s where s.billBook.id = :id")
    public List<SalesStatement> findSalesOnBillBookId(@Param(value="id") long id);

    @Query(value="Select Sum(s.quantity) from Sales s where s.product.id = :product and s.size.id = :size and (s.billBook.date between :startDate and :endDate)")
    public Double findSumOfSold(@Param(value="product") long product, @Param(value="size") long size, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    public List<BillBook> findByCustomer_IdAndDateBetween(long customerId, LocalDate startDate, LocalDate endDate);

    public List<BillBook> findByCustomer_IdAndDateBetweenAndSitesContainingIgnoreCase(long customerId, LocalDate startDate, LocalDate endDate, String sites);

    @Query("SELECT DISTINCT b.sites FROM BillBook b WHERE b.customer.id = :customerId AND b.sites IS NOT NULL AND b.sites <> ''")
    public List<String> findDistinctSitesByCustomerId(@Param("customerId") long customerId);
}
