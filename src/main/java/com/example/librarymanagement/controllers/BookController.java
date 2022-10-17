package com.example.librarymanagement.controllers;

import java.util.List;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BookLoan;
import com.example.librarymanagement.entities.dto.BookDto;
import com.example.librarymanagement.entities.dto.LoanBookDTO;
import com.example.librarymanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/library")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/book")
    public ResponseEntity<Book> createBook(@RequestBody BookDto book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getBooks() {return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);}

    @GetMapping(value = "/book/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable("bookId") int bookId) {
        return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/loanBook")
    public ResponseEntity<BookLoan> loanBook(@RequestBody LoanBookDTO loanBookDTO) {
        return new ResponseEntity<>(bookService.loanBook(loanBookDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/returnBook")
    public ResponseEntity<Void> returnBook(@RequestBody LoanBookDTO loanBookDTO) {
        bookService.returnBook(loanBookDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
