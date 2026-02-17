package com.akash.repository;

import com.akash.entity.LabourCost;
import com.akash.entity.LabourGroup;
import com.akash.entity.Product;
import com.akash.entity.Size;
import com.akash.repository.projections.LabourCostProj;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LabourCostRepository
extends CrudRepository<LabourCost, Long>,
PagingAndSortingRepository<LabourCost, Long> {
    public boolean existsByProductAndLabourGroupAndSize(Product product, LabourGroup labourGroup, Size size);

    public boolean existsByProductAndLabourGroupAndSizeAndIdNot(Product product, LabourGroup labourGroup, Size size, long id);

    @Query(value="Select l.rate from LabourCost l where l.product.id=:product and l.size.id=:size and l.labourGroup.id=:lg")
    public Double findRate(@Param(value="product") long product, @Param(value="size") long size, @Param(value="lg") long lg);

    public LabourCostProj findByProduct_IdAndSize_IdAndLabourGroup_Id(Long productId, Long sizeId, Long labourGroupId);
}
