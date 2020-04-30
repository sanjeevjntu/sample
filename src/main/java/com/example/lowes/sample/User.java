package com.example.lowes.sample;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "user name should not be blank")
    @Size(min = 5, max= 50)
    private  String name;
}
