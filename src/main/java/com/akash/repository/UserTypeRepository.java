package com.akash.repository;

import com.akash.entity.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserTypeRepository
extends CrudRepository<UserType, Long>,
PagingAndSortingRepository<UserType, Long> {
    public boolean existsByName(String name);

    @Query(value="select u from UserType u where u.name=:name and u.id!=:id")
    public UserType chechUserAlreadyExists(@Param(value="name") String name, @Param(value="id") long id);

    public UserType findByName(String name);
}
