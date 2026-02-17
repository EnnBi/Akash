package com.akash.repository;

import com.akash.entity.Product;
import com.akash.entity.Size;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository
extends CrudRepository<Product, Long>,
PagingAndSortingRepository<Product, Long> {
    public boolean existsByName(String name);

    @Query(value="select p from Product p where p.name=:name and p.id!=:id")
    public Product checkProductAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);

    @Query(value="Select p.sizes from Product p where p.id = :id")
    public List<Size> findSizesOnPrductId(@Param(value="id") long id);
}
