package com.example.project1.controller;

import com.example.project1.dto.PersonDTO;
import com.example.project1.entity.Person;
import com.example.project1.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/person")
public class MyController {
    private MyService service;

    @Autowired
    public MyController(MyService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return service.getAllPersons();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable Long id) {
        return service.getPersonById(id);
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        service.addPerson(person);
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable Long id, @RequestParam String surname) {
        service.updatePerson(id, surname);
    }
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
    }
}
