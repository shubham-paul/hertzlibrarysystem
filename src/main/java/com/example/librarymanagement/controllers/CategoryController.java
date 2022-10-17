package com.example.librarymanagement.controllers;

import java.util.List;

import com.example.librarymanagement.entities.Category;
import com.example.librarymanagement.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/library")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

}
