package com.akash.repository;

import com.akash.entity.CustomerStatement;
import com.akash.entity.DayBook;
import com.akash.entity.DealerStatement;
import com.akash.entity.DriverStatement;
import com.akash.entity.LabourStatement;
import com.akash.entity.OwnerStatement;
import com.akash.repository.custom.DayBookCustomizedRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DayBookRepository
extends CrudRepository<DayBook, Long>,
DayBookCustomizedRepository {
    public DayBook findByTransactionNumber(String transactionNumber);

    @Query(value="Select sum(d.amount) from DayBook d where d.date = :date and d.transactionType = :type")
    public Double findTotalAmountByDateAndType(@Param(value="date") LocalDate date, @Param(value="type") String type);

    @Query(value="Select sum(d.amount) from DayBook d where d.transactionType = 'Revenue'")
    public Double findTotalRevenue();

    @Query(value="Select sum(d.amount) from DayBook d where d.transactionType = 'Expenditure'")
    public Double findTotalExpenditure();

    @Query(value="Select sum(d.amount) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public Double findUserCredits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select sum(d.amount) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public Double findUserDebits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DriverStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public List<DriverStatement> findDriverCreditsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DriverStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public List<DriverStatement> findDriverDebitsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DealerStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public List<DealerStatement> findDealerCreditsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DealerStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public List<DealerStatement> findDealerDebitsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(d.transactionBy,d.amount,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public List<LabourStatement> findLabourStatementCreditsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(d.amount,d.transactionBy,d.date,d.transactionNumber,'DayBook') from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public List<LabourStatement> findLabourStatementDebitsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select sum(d.amount) from DayBook d where d.accountNumber= :accNum and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public Double findOwnerDebit(@Param(value="accNum") String accNum, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select sum(d.amount) from DayBook d where d.accountNumber = :accNum and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public Double findOwnerCredit(@Param(value="accNum") String accNum, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.OwnerStatement(d.date,d.user.name,d.transactionNumber,d.transactionType,d.transactionBy,d.accountNumber,d.responsiblePerson,d.amount) from DayBook d where d.accountNumber = :accNum and (d.date Between :startDate and :endDate)")
    public List<OwnerStatement> findByAccountNumberAndDateBetween(@Param(value="accNum") String accNum, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.CustomerStatement(d.amount,d.transactionBy,d.date,d.transactionNumber) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Revenue'")
    public List<CustomerStatement> findCustomerDebitsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.CustomerStatement(d.transactionBy,d.amount,d.date,d.transactionNumber) from DayBook d where d.user.id = :id and (d.date Between :startDate and :endDate) and d.transactionType = 'Expenditure'")
    public List<CustomerStatement> findCustomerCreditsBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);
}
