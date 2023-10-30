package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@Component
public class AppConfig implements TestConfig, TestFileNameProvider {

    @Value("${test.fileName}")
    private String testFileName;

    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }
}