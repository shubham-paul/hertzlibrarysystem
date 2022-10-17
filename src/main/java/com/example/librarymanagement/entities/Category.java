package com.example.librarymanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Category_tbl")
@Data
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;
}
