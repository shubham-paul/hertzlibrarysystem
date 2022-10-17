package com.example.librarymanagement.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.librarymanagement.entities.Book;
import com.example.librarymanagement.entities.BookLoan;
import com.example.librarymanagement.entities.Category;
import com.example.librarymanagement.entities.User;
import com.example.librarymanagement.entities.dto.BookDto;
import com.example.librarymanagement.entities.dto.LoanBookDTO;
import com.example.librarymanagement.exeptions.BookLimitExceededException;
import com.example.librarymanagement.exeptions.NotFoundException;
import com.example.librarymanagement.repositories.BookLoanRepository;
import com.example.librarymanagement.repositories.BookRepository;
import com.example.librarymanagement.repositories.CategoryRepository;
import com.example.librarymanagement.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    public void addBookTest() {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("testAuthor");
        bookDto.setTitle("testTitle");
        List<Integer> categories = new LinkedList<>();
        categories.add(1);
        bookDto.setCategories(categories);
        Category category = new Category();
        category.setId(1);
        category.setName("testCategory");
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(getBook());
        Book book = bookService.addBook(bookDto);
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getTitle()).isEqualTo("testTitle");
    }

    @Test
    public void addBookTestCategoryInvalid() {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("testAuthor");
        bookDto.setTitle("testTitle");
        List<Integer> categories = new LinkedList<>();
        categories.add(2);
        bookDto.setCategories(categories);
        Category category = new Category();
        category.setId(1);
        category.setName("testCategory");
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(getBook());
        Assert.assertThrows(NotFoundException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    public void loanBookTest() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(1);
        loanBookDTO.setUserId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));
        Mockito.when(bookLoanRepository.findBookLoanByUser(getUser())).thenReturn(List.of(getBookLoan()));
        Mockito.when(bookLoanRepository.save(Mockito.any(BookLoan.class))).thenReturn(getBookLoan());

        BookLoan bookLoan = bookService.loanBook(loanBookDTO);

        Assertions.assertThat(bookLoan).isNotNull();
    }

    @Test
    public void loanBookTestInvalidBookId() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(2);
        loanBookDTO.setUserId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));

        Assert.assertThrows(NotFoundException.class, () -> bookService.loanBook(loanBookDTO));
    }

    @Test
    public void loanBookTestInvalidUserId() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(1);
        loanBookDTO.setUserId(2);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));

        Assert.assertThrows(NotFoundException.class, () -> bookService.loanBook(loanBookDTO));
    }

    @Test
    public void loanBookTestLoanExceeded() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(1);
        loanBookDTO.setUserId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));
        Mockito.when(bookLoanRepository.findBookLoanByUser(getUser())).thenReturn(List.of(getBookLoan(), getBookLoan(), getBookLoan(), getBookLoan()));
        Mockito.when(bookLoanRepository.save(Mockito.any(BookLoan.class))).thenReturn(getBookLoan());

        Assert.assertThrows(BookLimitExceededException.class, () -> bookService.loanBook(loanBookDTO));
    }

    @Test
    public void returnBookTest() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(1);
        loanBookDTO.setUserId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));
        Mockito.when(bookLoanRepository.findBookLoanByUserAndBook(getUser(), getBook())).thenReturn(getBookLoan());

        bookService.returnBook(loanBookDTO);

        Mockito.verify(bookLoanRepository, Mockito.times(1)).delete(getBookLoan());
    }

    @Test
    public void returnBookTestInvalidBookId() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(2);
        loanBookDTO.setUserId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));

        Assert.assertThrows(NotFoundException.class, () -> bookService.returnBook(loanBookDTO));
    }

    @Test
    public void returnBookTestInvalidUserId() {
        LoanBookDTO loanBookDTO = new LoanBookDTO();
        loanBookDTO.setBookId(1);
        loanBookDTO.setUserId(2);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(getUser()));
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(getBook()));

        Assert.assertThrows(NotFoundException.class, () -> bookService.returnBook(loanBookDTO));
    }


    private Book getBook() {
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");

        Category category = new Category();
        category.setId(1);
        category.setName("testCategory");
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        book.setCategories(categories);

        return book;
    }

    private User getUser() {
        User user = new User();
        user.setUserId(1);
        user.setName("testName");
        return user;
    }

    private BookLoan getBookLoan() {
        BookLoan bookLoan = new BookLoan();
        bookLoan.setUser(getUser());
        bookLoan.setBook(getBook());
        return bookLoan;
    }
}
