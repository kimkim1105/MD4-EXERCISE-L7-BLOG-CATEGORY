package com.codegym.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Component
public class LocalDateFormatter implements Formatter<LocalDateTime> {
    private DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public String print(LocalDateTime date, Locale locale) {
        return date.format(formatter);
    }

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return null;
    }
}
