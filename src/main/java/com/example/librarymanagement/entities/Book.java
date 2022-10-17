package com.example.librarymanagement.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "book")
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue
    private int id;

    private String author;

    @ManyToMany
    @JoinColumn(name = "categoryId")
    private Set<Category> categories = new HashSet<>();

    private String title;

    private boolean isBookAvailable = true;
}
