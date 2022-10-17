package com.example.librarymanagement.entities.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String author;
    private List<Integer> categories;
}
