package com.example.librarymanagement.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "User_tbl")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private int userId;

    private String name;
}
