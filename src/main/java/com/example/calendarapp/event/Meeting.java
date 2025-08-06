package com.example.calendarapp.event;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Set;

public class Meeting extends AbstractEvent {

    Set<String> participants;
    private String meetingRoom;
    private String agenda;

    public Meeting(int id, String title, ZonedDateTime startAt, ZonedDateTime endAt, Set<String> participants, String meetingRoom, String agenda) {
        super(id, title, startAt, endAt); // 부모에게 필드 전달

        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.agenda = agenda;
    }

    @Override
    public void print() {
        System.out.printf("[회의] %s : %s%n", getTitle(), agenda);
    }
}
