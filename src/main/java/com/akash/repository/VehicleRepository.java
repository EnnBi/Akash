package com.akash.repository;

import com.akash.entity.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository
extends CrudRepository<Vehicle, Long>,
PagingAndSortingRepository<Vehicle, Long> {
    public boolean existsByNumber(String number);

    @Query(value="select v from Vehicle v where v.number=:num and v.id!=:id")
    public Vehicle checkVehicleAlreadyExists(@Param(value="num") String num, @Param(value="id") long id);
}
