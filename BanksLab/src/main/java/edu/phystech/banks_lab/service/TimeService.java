package edu.phystech.banks_lab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private long shiftDays = 0;

    public void addDays(int days) {
        shiftDays += days;
    }

    public void addMonths(int months) {
        LocalDate date = LocalDate.ofEpochDay(shiftDays);

        shiftDays = date.plusMonths(months).toEpochDay();
    }

    public void addYears(int years) {
        LocalDate date = LocalDate.ofEpochDay(shiftDays);

        shiftDays = date.plusYears(years).toEpochDay();
    }
    public String getCurrentTimePretty() {
        LocalTime localTime = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.ofEpochDay(shiftDays), localTime);
        return dateTime.toString();
    };

    public long getCurrentTime() {
        return shiftDays * 24L * 60L * 60L * 1000L + System.currentTimeMillis();
    }

    public int diffMonths(long time) {
        LocalTime localTime = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.ofEpochDay(shiftDays), localTime);
        dateTime.minus(time, ChronoUnit.MILLIS);

        return dateTime.getYear() * 12 + dateTime.getMonthValue();
    }
}
