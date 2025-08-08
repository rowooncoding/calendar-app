package com.example.calendarapp;

import ch.qos.logback.classic.joran.sanity.IfNestedWithinSecondPhaseElementSC;
import com.example.calendarapp.event.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class CalendarAppApplication {

    public static void main(String[] args) {
        // SpringApplication.run(CalendarAppApplication.class, args);

        // 미팅1번 만들기
        List<AbstractEvent> list = new ArrayList<>();
        HashSet<String> participants = new HashSet<>();
        participants.add("danny.kim");

        Meeting meeting1 = new Meeting(
                1, "meeting1",
                ZonedDateTime.now(), ZonedDateTime.now().plusHours(1),
                participants, "meetingRoomA", "스터디"
        );

        list.add(meeting1);

        // 투두 1번 만들기
        Todo todo1 = new Todo(
                2, "todo1",
                ZonedDateTime.now(), ZonedDateTime.now().plusHours(2),
                "할 일 적기"
        );

        list.add(todo1);

        list.forEach(Event::print);

        // 미팅만 필터링하기
        /* 기존에 내가 구현한 것
        List<Meeting> meetingList = list.stream()
                .filter(item -> item instanceof Meeting)
                .map(item -> (Meeting) item)
                .toList();
        * */

        // 개선
        // enum을 사용하여 각각의 Event 클래스에서 메서드로 처리
        // 만약 미팅이 아닌 투두 같은것만 보여줘야 한다면 support의 인자만 변경해주면 되는 방식 -> 확장성 증가
        list.stream()
                .filter(each -> each.support(EventType.MEETING))
                .forEach(Event::print);
    }

}
