package com.example.calendarapp;

import ch.qos.logback.classic.joran.sanity.IfNestedWithinSecondPhaseElementSC;
import com.example.calendarapp.event.*;
import com.example.calendarapp.reader.EventCsvReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class CalendarAppApplication {

    public static void main(String[] args) throws IOException, CsvException {
        // SpringApplication.run(CalendarAppApplication.class, args);

        Schedule schedule = new Schedule();

        EventCsvReader csvReader = new EventCsvReader();
        String meetingCsvPath = "/data/meeting.csv";

        List<Meeting> meetings = csvReader.readMeetings(meetingCsvPath);
        meetings.forEach(schedule::add);

        schedule.printAll();
    }

}
