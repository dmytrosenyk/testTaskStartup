package com.example.project1.entity;



import javax.persistence.*;
import java.time.LocalDate;


@Table
@Entity
public class Person {
    @Id
    @SequenceGenerator(
            name = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private LocalDate dateOfBirth;
    public Person() {
    }

    public Person(Long id, String name, String surname,  LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
    public Person(String name, String surname,  LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
