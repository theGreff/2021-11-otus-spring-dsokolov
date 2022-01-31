package ru.otus.dsokolov.dto;

import ru.otus.dsokolov.domain.Author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthorDto {

    private long id;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 100, message = "{name-field-should-has-expected-size}")
    private String fullName;

    public AuthorDto() {
    }

    public AuthorDto(String name) {
        this.fullName = name;
    }

    public AuthorDto(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Author toDomainObject() {
        return new Author(id, fullName);
    }

}
