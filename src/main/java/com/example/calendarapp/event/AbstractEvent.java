package com.example.calendarapp.event;

import java.time.Duration;
import java.time.ZonedDateTime;

public abstract class AbstractEvent implements Event {
    private final int id;
    private String title;

    private ZonedDateTime startAt;
    private ZonedDateTime endAt;
    private Duration duration;

    private final ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    private boolean deletedYn;

    public AbstractEvent(int id,
                            String title,
                            ZonedDateTime startAt,
                            ZonedDateTime endAt,
                            Duration duration,
                            ZonedDateTime createdAt,
                            ZonedDateTime modifiedAt,
                            boolean deletedYn) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.duration = Duration.between(startAt, endAt); // 값이 들어올 때 바로 계산

        // 생성, 수정은 필드가 엽데이트 된 현재 날짜를 넣어줘야함.
        ZonedDateTime now = ZonedDateTime.now();
        this.createdAt = now;
        this.modifiedAt = now;

        this.deletedYn = false;
    }
}
