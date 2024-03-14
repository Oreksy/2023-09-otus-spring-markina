package ru.otus.hw;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Application {

//http://localhost:8080/authors
//http://localhost:8080/authors/edit/1
//http://localhost:8080/genres
//http://localhost:8080/books

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
