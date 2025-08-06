# 일정관리 프로그램

- 구글 캘린더 등 일정을 관리할 수 있는 서비스
- JAVA 기반으로 작성되었으며 콘솔에서 결과를 확인할 수 있습니다.

# 요구사항

일정관리 프로그램의 요구사항은 다음과 같습니다.

## 일정의 유형

- “미팅”, “할 일”, “방해 금지 시간”, “외출”에 대한 일정을 관리할 수 있다.
    - 미팅 : `meeting`
    - 할일 : `to-do`
    - 방해 금지 시간 : `no-disturbance`
    - 외출 : `out-of-office`

## 등록 (Create)

- 일정을 등록할 수 있다
    - 겹치는 일정이 존재하면 일정을 등록할 수 없다.
- 일정을 .csv 파일로 대량 등록할 수 있다.

## 검색/조회 (Read)

- 일정을 ID, 이름, 기간으로 검색할 수 있다.
- 특정 일자를 입력하면 해당 일자에 등록된 일정을 요약하여 보여준다.

## 수정 (Update)

- 일정을 수정할 수 있다.
- 삭제된 일정은 수정할 수 없다.

## 삭제 (Delete)

- 일정을 삭제할 수 있다.
- Soft-delete 로 처리한다.

# 시스템 디자인 및 설계

```java
// 추후에 이벤트 타입이 추가될 것을 고려하여 이벤트 타입을 enum클래스로 작성
public enum EventType {
	MEETING,
	TO_DO,
	NO_DISTURBANCE,
	OUT_OF_OFFICE,
	;
}

// 이벤트 타입들은 하나의 이벤트라고 생각
public interface Event {
	void print(); // 각 이벤트에 대한 결과에 출력이 필요
}

// 공통 필드를 다루기 위하여 추상 클래스 생성
// 이 추상 클래스를 상속받아서 사용하는 자식 클래스에서는 공통 필드를 고려하지 않아도 됨.
// 개별 자식 클래스를 하나의 타입으로 보고 처리 가능
public abstract class AbstractEvent implements Event {
	private int id;
	private String title;
	private ZoneDateTime startAt;
	private ZoneDateTime endAt;
	
	private boolean deletedYn;
	
	private ZoneDateTime createdAt;
	private ZoneDateTime modifiedAt;
	
	// 자식 클래스에서 각 필드들에 대한 업데이트가 필요할 수 있기 때문에 강제구현
	protected abstract void update();
}

public class Meeting extends AbstractEvent {
	private String description;
	private Set<String> participants;
}

public class Todo extends AbstractEvent {

}

public class OutOfOffice extends AbstractEvent {

}

public class NoDisturbance extends AbstractEvent {

}
```

> 이 이벤트 들이 여러개 모인 것을 Schedule이라고 한다.
> 

# CSV 읽기

- open-csv 오픈소스 사용
