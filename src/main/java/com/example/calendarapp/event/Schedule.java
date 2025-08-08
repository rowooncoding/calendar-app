package com.example.calendarapp.event;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<AbstractEvent> events = new ArrayList<>();

    public void add(AbstractEvent event) {

        if (hasScheduleConflictWith(event)) {
            return;
        }

        this.events.add(event);
    }

    public void printAll() {
        events.forEach(Event::print);
    }

    public void printBy(EventType type) {
        events.stream().filter(event -> event.support(type)).forEach(Event::print);
    }

    // 시작시간 겹치는 것 유효성 처리
    // 새로 등록한 이벤트의 시작 시간이 이미 등록된 이벤트의 종료시각보다 느리다면, 그건 등록되면 안되는(시간이 겹치는) 이벤트임
    /*
    * 모든 경우의 수
    * 1. 새로 등록 될 이벤트의 종료시간이 이미 등록된 이벤트의 시작시간 전이고, 새로 등록 될 이벤트의 시작시간이 이미 등록된 시작시간보다 느릴 때
    * 혹은
    * 2. 새로 등록 될 이벤트의 시작시간이 이미 등록된 이벤트의 종료시각보다 느리고 새로 등록 될 이벤트의 종료시간이 이미 등록된 이벤트의 종료시간보다 빠를 때
    * */
    private boolean hasScheduleConflictWith(AbstractEvent event) {
        return events.stream()
                .anyMatch(each ->
                        (event.getStartAt().isBefore(each.getEndAt()) && event.getStartAt().isAfter(each.getStartAt()))
                        || (event.getEndAt().isAfter(each.getStartAt())) && event.getEndAt().isBefore(each.getEndAt())
                );
    }
}
