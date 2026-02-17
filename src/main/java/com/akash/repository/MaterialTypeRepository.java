package com.akash.repository;

import com.akash.entity.MaterialType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MaterialTypeRepository
extends CrudRepository<MaterialType, Long>,
PagingAndSortingRepository<MaterialType, Long> {
    public boolean existsByName(String name);

    @Query(value="select m from MaterialType m where m.name=:name and m.id!=:id")
    public MaterialType checkMaterialAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);
}
