package ru.otus.hw.exceptions;

public class FileNotFindException extends RuntimeException {
    public FileNotFindException(String message) {
        super(message);
    }
}
