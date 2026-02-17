package com.akash.repository;

import com.akash.entity.Sales;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepository
extends CrudRepository<Sales, Long> {
}
