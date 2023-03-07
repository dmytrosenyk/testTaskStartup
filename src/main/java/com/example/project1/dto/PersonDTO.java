package com.example.project1.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PersonDTO {
    private String name;
    private String surname;
    private Integer age;
}
