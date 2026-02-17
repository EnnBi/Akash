package com.akash.controller;

import com.akash.entity.ToDo;
import com.akash.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/todo"})
public class ToDoController {
    @Autowired
    ToDoRepository todoRepository;

    @GetMapping
    public ResponseEntity<?> view() {
        return ResponseEntity.ok(this.todoRepository.findByActiveOrderByDateDesc(true));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ToDo toDo) {
        toDo.setActive(true);
        return ResponseEntity.ok((Object)((ToDo)this.todoRepository.save(toDo)));
    }

    @GetMapping(value={"/delete/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        ToDo toDo = this.todoRepository.findById(id).orElse(null);
        if (toDo != null) {
            toDo.setActive(false);
        }
        this.todoRepository.save(toDo);
        return ResponseEntity.ok().build();
    }
}
