package com.example.project1;
import static org.junit.jupiter.api.Assertions.*;


import com.example.project1.dao.MyRepository;
import com.example.project1.dto.PersonDTO;
import com.example.project1.entity.Person;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MyRepository repository;

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person("John", "Doe", LocalDate.of(1990, 1, 1));
        Person person2 = new Person("Jane", "Doe", LocalDate.of(1991, 2, 2));
        repository.saveAll(Arrays.asList(person1, person2));

        ResponseEntity<List<PersonDTO>> response = restTemplate.exchange(
                "/api/person",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDTO>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size()>0);
    }

    @Test
    public void testGetPersonById() {
        Person person = new Person("John", "Doe", LocalDate.of(1990, 1, 1));
        repository.save(person);

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity(
                "/api/person/" + person.getId(),
                PersonDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person.getName(), response.getBody().getName());
        assertEquals(person.getSurname(), response.getBody().getSurname());
    }

    @Test
    public void testAddPerson() {
        Person person = new Person(1L,"John", "Doeq", LocalDate.of(1990, 1, 1));
        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> response = restTemplate.postForEntity(
                "/api/person",
                request,
                Person.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Person savedPerson = repository.findById(person.getId()).orElse(null);
        assertNotNull(savedPerson);
        assertEquals(person.getName(), savedPerson.getName());
        assertEquals(person.getSurname(), savedPerson.getSurname());
        assertEquals(person.getDateOfBirth(), savedPerson.getDateOfBirth());
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person("John", "Doe", LocalDate.of(1990, 1, 1));
        repository.save(person);

        String newSurname = "Smith";
        restTemplate.put(
                "/api/person/" + person.getId() + "?surname=" + newSurname,
                null);

        Person updatedPerson = repository.findById(person.getId()).orElse(null);
        assertNotNull(updatedPerson);
        assertEquals(newSurname, updatedPerson.getSurname());
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person("John", "Doe", LocalDate.of(1990, 1, 1));
        repository.save(person);
        restTemplate.delete("/api/person/" + person.getId());
        assertFalse(repository.existsById(person.getId()));
    }

}
