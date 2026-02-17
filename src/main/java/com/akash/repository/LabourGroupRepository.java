package com.akash.repository;

import com.akash.entity.LabourGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LabourGroupRepository
extends CrudRepository<LabourGroup, Long>,
PagingAndSortingRepository<LabourGroup, Long> {
    public boolean existsByName(String name);

    @Query(value="select l from LabourGroup l where l.name=:name and l.id!=:id")
    public LabourGroup checkLabourGroupAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);
}
