package com.example.librarymanagement.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BookLoan;
import com.example.librarymanagement.entities.Category;
import com.example.librarymanagement.entities.User;
import com.example.librarymanagement.entities.dto.BookDto;
import com.example.librarymanagement.entities.dto.LoanBookDTO;
import com.example.librarymanagement.exeptions.BookLimitExceededException;
import com.example.librarymanagement.exeptions.BookNotAvailableException;
import com.example.librarymanagement.exeptions.NotFoundException;
import com.example.librarymanagement.repositories.BookLoanRepository;
import com.example.librarymanagement.repositories.BookRepository;
import com.example.librarymanagement.repositories.CategoryRepository;
import com.example.librarymanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookService {

    private static final Integer MAX_LOAN_LIMIT = 3;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Book addBook(final BookDto bookDto) {

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        Set<Category> categories = new HashSet<>();
        bookDto.getCategories().forEach((categoryId) -> categories.add(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"))));
        book.setCategories(categories);

        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(final int bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("book not found"));
    }

    public void deleteBook(final int bookId) {
         bookRepository.deleteById(bookId);
    }

    public BookLoan loanBook(final LoanBookDTO loanBookDTO) {
        User user = userRepository.findById(loanBookDTO.getUserId()).orElseThrow(() -> new NotFoundException("user not found"));
        Book book = bookRepository.findById(loanBookDTO.getBookId()).orElseThrow(() -> new NotFoundException("book not found"));

        if(!book.isBookAvailable()) throw new BookNotAvailableException("Book Not Available");

        List<BookLoan> bookLoans = bookLoanRepository.findBookLoanByUser(user);

        if(bookLoans.size() >= MAX_LOAN_LIMIT) {
            throw  new BookLimitExceededException("Book Limit has exceeded the max limit of: " + MAX_LOAN_LIMIT);
        }

        BookLoan bookLoan = new BookLoan();
        bookLoan.setBook(book);
        bookLoan.setUser(user);

        book.setBookAvailable(false);
        bookRepository.save(book);

        return bookLoanRepository.save(bookLoan);
    }

    public void returnBook(final LoanBookDTO loanBookDTO) {
        User user = userRepository.findById(loanBookDTO.getUserId()).orElseThrow(() -> new NotFoundException("user not found"));
        Book book = bookRepository.findById(loanBookDTO.getBookId()).orElseThrow(() -> new NotFoundException("book not found"));

        BookLoan bookLoan = bookLoanRepository.findBookLoanByUserAndBook(user, book);
        if(bookLoan == null) {
            throw new NotFoundException("No record found with userId: " + loanBookDTO.getUserId() + " and BookId: " + loanBookDTO.getBookId());
        }
        book.setBookAvailable(true);
        bookRepository.save(book);
        bookLoanRepository.delete(bookLoan);
    }
}
