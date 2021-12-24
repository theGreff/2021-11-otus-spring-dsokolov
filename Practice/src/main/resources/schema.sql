DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;
CREATE TABLE AUTHOR(ID BIGINT AUTO_INCREMENT PRIMARY KEY, FULLNAME VARCHAR(255));

CREATE TABLE GENRE(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

CREATE TABLE BOOK(
ID BIGINT PRIMARY KEY NOT NULL,
TITLE VARCHAR(255) NOT NULL,
IDAUTHOR INT NOT NULL,
FOREIGN KEY (IDAUTHOR) REFERENCES AUTHOR(ID),
IDGENRE INT NOT NULL,
FOREIGN KEY (IDGENRE) REFERENCES GENRE(ID));

CREATE UNIQUE INDEX IDX_BOOK_TITLE on BOOK (TITLE);

CREATE SEQUENCE SEQ_BOOK START WITH 1 INCREMENT BY 1 MINVALUE 1;
