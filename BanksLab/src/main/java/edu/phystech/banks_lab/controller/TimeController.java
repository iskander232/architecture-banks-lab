package edu.phystech.banks_lab.controller;

import edu.phystech.banks_lab.service.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("time")
@AllArgsConstructor
public class TimeController {

    TimeService timeService;

    @GetMapping
    public String getCurrent() {
        return timeService.getCurrentTimePretty();
    }

    @GetMapping("days/{amount}")
    public String addDays(@PathVariable int amount) {
        timeService.addDays(amount);
        return timeService.getCurrentTimePretty();
    }

    @GetMapping("months/{amount}")
    public String addMonths(@PathVariable int amount) {
        timeService.addMonths(amount);
        return timeService.getCurrentTimePretty();
    }

    @GetMapping("years/{amount}")
    public String addYears(@PathVariable int amount) {
        timeService.addYears(amount);
        return timeService.getCurrentTimePretty();
    }
}
