insert into authors(full_name)
values ('Александр Сергеевич Пушкин'), ('Лев Николаевич Толстой'), ('Виктор Олегович Пелевин');

insert into genres(name)
values ('рассказ'), ('роман'), ('сатира');

insert into books(title, author_id, genre_id)
values ('Метель', 1, 1), ('Война и мир', 2, 2), ('Чапаев и Пустота', 3, 3);

insert into Comments(context, book_id)
values ('Хороший рассказ', 1), ('Скучно', 2), ('Интересно', 3);

insert into users(user_name, user_password)
values ('Valya','$2a$10$0NoI/0kvQp5BuxO3epBK4OUUFYZ8DmednRKezERCjEc457w6Pw8yy')
