package com.akash.repository;

import com.akash.entity.CustomerStatement;
import com.akash.entity.GoodsReturn;
import com.akash.repository.custom.GoodsReturnCustomizedRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GoodsReturnRepository
extends CrudRepository<GoodsReturn, Long>,
PagingAndSortingRepository<GoodsReturn, Long>,
GoodsReturnCustomizedRepository {
    @Query(value="Select sum(d.total) from GoodsReturn d where d.customer.id = :id and (d.date Between :startDate and :endDate)")
    public Double findUserGoodsReturn(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.CustomerStatement(d.total,d.date,d.receiptNumber) from GoodsReturn d where d.customer.id = :id and (d.date Between :startDate and :endDate)")
    public List<CustomerStatement> findCustomerGoodsReturnBetweenDates(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);
}
