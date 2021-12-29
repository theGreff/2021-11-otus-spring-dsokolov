package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.Book;

import java.util.List;

interface BookService {

    List<Book> findAll();

    Book getBookByTitle(String bookTitle);

    long getCount();

    Book createBook(String title, String authorName, String genreName);

    void deleteBook(String title);

    void changeTitle(String title, String titleNew);

    void changeAuthor(String bookTitle, String authorFullNameNew);

    void changeGenre(String bookTitle, String genreNameNew);
}
