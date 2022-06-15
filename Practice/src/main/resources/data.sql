insert into GENRE (id, name) values (1, 'фантастика');
insert into GENRE (id, name) values (2, 'детектив');
insert into GENRE (id, name) values (3, 'фентези');
insert into GENRE (id, name) values (4, 'ужасы');

insert into AUTHOR (id, fullName) values (1, 'Джоан Роулинг');
insert into AUTHOR (id, fullName) values (2, 'Агата Кристи');
insert into AUTHOR (id, fullName) values (3, 'Сергей Лукьяненко');
insert into AUTHOR (id, fullName) values (4, 'Роберт Шекли');
insert into AUTHOR (id, fullName) values (5, 'Стивен Кинг');
insert into AUTHOR (id, fullName) values (6, 'Рей Бредбери');

insert into BOOK (id, TITLE, ID_AUTHOR, ID_GENRE) values (1, 'Первая книга', 5, 4);
insert into BOOK (id, TITLE, ID_AUTHOR, ID_GENRE) values (2, 'Вторая книга', 1, 3);
insert into BOOK (id, TITLE, ID_AUTHOR, ID_GENRE) values (3, 'Третья книга', 2, 2);

insert into USER (ID , LOGIN, PASSWORD, ENABLED, ROLE) values (1, 'Admin', 'Admin', 'true', 'ADMIN');
insert into USER (ID , LOGIN, PASSWORD, ENABLED, ROLE) values (2, 'User', 'User', 'true', 'USER');
insert into USER (ID , LOGIN, PASSWORD, ENABLED, ROLE) values (3, 'User2', 'User2', 'false', 'USER');