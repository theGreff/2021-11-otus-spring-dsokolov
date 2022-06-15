package ru.otus.dsokolov.domain;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FULLNAME", nullable = false, unique = true)
    private String fullName;

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

    public Author(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Author() {
    }
}
