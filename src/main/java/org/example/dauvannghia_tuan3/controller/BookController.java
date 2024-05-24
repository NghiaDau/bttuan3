package org.example.dauvannghia_tuan3.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.dauvannghia_tuan3.entities.Book;
import org.example.dauvannghia_tuan3.services.BookService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public String showAllBook(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "/book/list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book",new Book());
        return "/book/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book){
        if(bookService.getBookById(book.getId()).isEmpty())
            bookService.addBook(book);
        return "redirect:/books";

    }

    @GetMapping("/edit/{id}")
    public String editBookForm( Model model, @PathVariable long id) {
        var book = bookService.getBookById(id).orElse(null);
        model.addAttribute("book", book != null ? book : new Book());
        return "/book/edit";
    }
    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        if (bookService.getBookById(id).isPresent())
            bookService.deleteBookById(id);
        return "redirect:/books";
    }
}
