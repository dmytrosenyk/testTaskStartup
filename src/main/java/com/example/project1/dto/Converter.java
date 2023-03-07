package com.example.project1.dto;

import com.example.project1.dao.MyRepository;
import com.example.project1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {
    private final MyRepository repository;

    @Autowired
    public Converter(MyRepository repository) {
        this.repository = repository;
    }

    public PersonDTO personEntityToDTO(Person person){
        PersonDTO personDTO=new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setAge(Period.between(person.getDateOfBirth(),LocalDate.now()).getYears());
        return personDTO;
    }

    public List<PersonDTO> listPersonEntityToDTO(List<Person> personList){
        return personList.stream().map(this::personEntityToDTO).collect(Collectors.toList());
    }

}
