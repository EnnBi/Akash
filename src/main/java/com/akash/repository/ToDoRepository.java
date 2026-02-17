package com.akash.repository;

import com.akash.entity.ToDo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository
extends CrudRepository<ToDo, Long> {
    public List<ToDo> findByActiveOrderByDateDesc(boolean active);
}
