package ru.otus.hw.model;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
