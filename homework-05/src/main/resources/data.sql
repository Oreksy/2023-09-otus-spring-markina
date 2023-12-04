insert into authors(full_name)
values ('Александр Сергеевич Пушкин'), ('Лев Николаевич Толстой'), ('Виктор Олегович Пелевин');

insert into genres(name)
values ('рассказ'), ('роман'), ('сатира');

insert into books(title, author_id, genre_id)
values ('Метель', 1, 1), ('Война и мир', 2, 2), ('Чапаев и Пустота', 3, 3);
