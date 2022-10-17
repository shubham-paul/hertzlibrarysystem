package com.example.librarymanagement.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "bookLoan")
@Entity
@Data
public class BookLoan {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name="bookId")
    private Book book;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
