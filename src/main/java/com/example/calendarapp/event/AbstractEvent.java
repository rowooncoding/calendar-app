package com.example.calendarapp.event;

import com.example.calendarapp.event.update.AbstractAuditableEvent;
import com.example.calendarapp.exception.InvalidEventException;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.ZonedDateTime;

@Getter @Setter
public abstract class AbstractEvent implements Event {

    private final int id;
    private String title;

    private ZonedDateTime startAt;
    private ZonedDateTime endAt;
    private Duration duration;

    private final ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    private boolean deletedYn;

    protected AbstractEvent(int id, String title, ZonedDateTime startAt, ZonedDateTime endAt) {

        // startAt과 endAt 유효성 처리
        if (startAt.isAfter(endAt)) {
            throw new InvalidEventException(String.format("시작일은 종료일보다 이전이어야 합니다. 시작일=%s, 종료일=%s", startAt, endAt));
        }

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

    public void validateAndUpdate(AbstractAuditableEvent update) {
        if (deletedYn) {
            throw new RuntimeException("이미 삭제된 이벤트는 수정할 수 없습니다");
        }

        defaultUpdate(update);

        update(update);
    }

    private void defaultUpdate(AbstractAuditableEvent update) {
        this.title = update.getTitle();
        this.startAt = update.getStartAt();
        this.endAt = update.getEndAt();
        this.duration = Duration.between(this.startAt, this.endAt);
        this.modifiedAt = ZonedDateTime.now();
    }

    protected abstract void update(AbstractAuditableEvent update);
}
