package com.akash.repository;

import com.akash.entity.AppUser;
import com.akash.entity.LabourStatement;
import com.akash.entity.Manufacture;
import com.akash.projections.ChartProjection;
import com.akash.repository.custom.ManufactureCustomizedRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ManufactureRepository
extends CrudRepository<Manufacture, Long>,
ManufactureCustomizedRepository {
    @Query(value="Select sum(m.totalQuantity) as quantity,m.date as date,m.product.name as name from Manufacture m where m.date between :startDate and :endDate group by m.date,m.product")
    public List<ChartProjection> findQuantityGroupByDateAndProductBetweenDates(@Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select sum(l.amountPerHead) from LabourInfo l where :labour MEMBER OF l.labours and l.manufacture.date between :startDate and :endDate ")
    public Double sumManufactureDebits(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select new com.akash.entity.LabourStatement(l.manufacture.date,l.manufacture.product.name,l.manufacture.size.name,l.quantity,l.amountPerHead,'Manufactre') from LabourInfo l where :labour MEMBER OF l.labours and l.manufacture.date between :startDate and :endDate ")
    public List<LabourStatement> findLabourDebits(@Param(value="labour") AppUser labour, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);

    @Query(value="Select Sum(m.totalQuantity) from  Manufacture m where m.product.id = :product and m.size.id = :size and (m.date between :startDate and :endDate)")
    public Double findSumOfManufactured(@Param(value="product") long product, @Param(value="size") long size, @Param(value="startDate") LocalDate startDate, @Param(value="endDate") LocalDate endDate);
}
