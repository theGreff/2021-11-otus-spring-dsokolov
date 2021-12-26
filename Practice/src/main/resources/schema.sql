DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;

CREATE TABLE AUTHOR(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    FULLNAME    VARCHAR(255)
);

CREATE TABLE GENRE(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME    VARCHAR(255)
);

CREATE TABLE BOOK(
    ID              BIGINT PRIMARY KEY NOT NULL,
    TITLE           VARCHAR(255) NOT NULL,
    ID_AUTHOR       BIGINT NOT NULL,
    ID_GENRE        BIGINT NOT NULL,
    FOREIGN KEY (ID_AUTHOR) REFERENCES AUTHOR(ID),
    FOREIGN KEY (ID_GENRE) REFERENCES GENRE(ID)
);

CREATE UNIQUE INDEX IDX_BOOK_TITLE on BOOK (TITLE);

CREATE SEQUENCE SEQ_BOOK START WITH 1 INCREMENT BY 1 MINVALUE 1;

CREATE TABLE BOOK_COMMENT(
    ID          BIGINT PRIMARY KEY NOT NULL,
    COMMENT     VARCHAR(1000) NOT NULL,
    DATE_INSERT Date,
    ID_BOOK     BIGINT NOT NULL
);

CREATE INDEX IDX_BOOK_COMMENT_ID_BOOK  on BOOK_COMMENT(ID_BOOK);

CREATE SEQUENCE SEQ_BOOK_COMMENT START WITH 1 INCREMENT BY 1 MINVALUE 1;

