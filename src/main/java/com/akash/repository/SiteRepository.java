package com.akash.repository;

import com.akash.entity.Site;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SiteRepository
extends CrudRepository<Site, Long>,
PagingAndSortingRepository<Site, Long> {
    public boolean existsByName(String name);

    @Query(value="select s from Site s where s.name=:name and s.id!=:id")
    public Site checkSiteAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);
}
