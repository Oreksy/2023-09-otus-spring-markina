package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Setter
@Component
@ConfigurationProperties(prefix = "test")
public class AppConfig implements TestConfig, TestFileNameProvider, LocaleConfig {

    @Getter
    private Locale locale;

    private int rightAnswersCountToPass;

    private Map<String,String> fileNameByLocaleTag;

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }
}
