package com.akash.repository;

import com.akash.entity.ClearDues;
import com.akash.entity.CustomerStatement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ClearDuesRepository
extends CrudRepository<ClearDues, Long>,
PagingAndSortingRepository<ClearDues, Long> {
    @Query(value="Select sum(d.amount) from ClearDues d where d.user.id = :id and (d.date Between :startDate and :endDate)")
    public Double findUserClearDues(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.CustomerStatement(d.amount,d.date,d.ownerName.name) from ClearDues d where d.user.id = :id and (d.date Between :startDate and :endDate)")
    public List<CustomerStatement> findCustomerClearDuesBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);
}
