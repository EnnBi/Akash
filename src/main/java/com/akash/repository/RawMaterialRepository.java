package com.akash.repository;

import com.akash.entity.DealerStatement;
import com.akash.entity.RawMaterial;
import com.akash.repository.custom.RawMaterialCustomizedRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RawMaterialRepository
extends CrudRepository<RawMaterial, Long>,
RawMaterialCustomizedRepository {
    public boolean existsByChalanNumber(String chalanNumber);

    @Query(value="select r from RawMaterial r where r.chalanNumber=:chalanNum and r.id!=:id")
    public RawMaterial checkRawMaterialAlreadyExists(@Param(value="chalanNum") String chalanNum, @Param(value="id") long id);

    @Query(value="Select Sum(r.amount) from RawMaterial r where r.dealer.id = :id and r.date between :startDate and :endDate")
    public Double sumDebits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.DealerStatement(r.date,r.chalanNumber,r.material.name,r.quantity,r.unit,r.amount,'RawMaterial') from RawMaterial r where r.dealer.id = :id and r.date between :startDate and :endDate ")
    public List<DealerStatement> findDealerDebits(@Param(value="id") long id, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);
}
