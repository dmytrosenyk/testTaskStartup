package com.example.project1.service;

import com.example.project1.dao.MyRepository;
import com.example.project1.dto.Converter;
import com.example.project1.dto.PersonDTO;
import com.example.project1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyService {

    private final MyRepository repository;
    private final Converter converter;

    @Autowired
    public MyService(MyRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }
    @Transactional
    public List<PersonDTO> getAllPersons() {
        return converter.listPersonEntityToDTO(repository.findAll());
    }
    @Transactional
    public PersonDTO getPersonById(Long id) {
        return converter.personEntityToDTO(repository.findById(id).orElseThrow(()->new IllegalStateException("person not found")));
    }
    @Transactional
    public void addPerson(Person person) {
        if (person.getId()!=null) {
            if (repository.findById(person.getId()).isPresent())
                throw new IllegalStateException("person is found");
        }
        if (person.getSurname()==null||person.getName()==null||person.getDateOfBirth()==null)
            throw new IllegalArgumentException("wrong data input");
        repository.save(person);
    }
    @Transactional
    public void updatePerson(Long id,String surname) {
        Person person=repository.findById(id).orElseThrow(()->new IllegalStateException("person not found"));
        if (surname==null|| surname.equals(person.getSurname()) || surname.equals("")){
            throw new IllegalArgumentException("wrong data input");
        }
        person.setSurname(surname);
        repository.save(person);
    }
    @Transactional
    public void deletePerson(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new IllegalStateException("person not found");
        }
    }
}
