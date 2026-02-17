package com.akash.repository;

import com.akash.entity.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SizeRepository
extends CrudRepository<Size, Long>,
PagingAndSortingRepository<Size, Long> {
    public boolean existsByName(String name);

    @Query(value="select s from Size s where s.name=:name and s.id!=:id")
    public Size checkSizeAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);
}
