package com.example.librarymanagement.repositories;

import java.util.List;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BookLoan;
import com.example.librarymanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    List<BookLoan> findBookLoanByUser(User user);
    BookLoan findBookLoanByUserAndBook(User user, Book book);
}
