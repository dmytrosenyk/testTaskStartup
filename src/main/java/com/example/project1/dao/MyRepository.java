package com.example.project1.dao;

import com.example.project1.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository<Person, Long> {
}
