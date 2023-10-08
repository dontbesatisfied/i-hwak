package com.onemmp.ihawk.api.exception

import java.io.Serializable


class ErrorResponse(
    val message: String,
    val code: String,
    val details: Any?
) {

}

// 자바에서 java.io.Serializable 인터페이스를 보면 구현해야 하는 메서드가 없다.
// 그 이유는 Serializable 인터페이스를 구현한 구현체가 직렬화 대상이다라는 것을 JVM에게 알려주는 역할만 하기 때문이다.

// DTO, VO 객체 클래스를 보면 Serializabe 인터페이스를 구현한 코드를 많이 확인할 수 있는데, 객체를 담을 클래스는 기본적으로 Serializable을 구현하는 것을 권장한다고 한다. 직렬화 자체가 오버헤드의 원인이 되기도 하지만, Serializable은 해당 클래스의 인스턴스를 직렬화가 가능하다는 것을 선언하는 것 뿐이니, 설계를 고려해서 잠재적으로 직렬화 가능성이 있는 유형은 Serializable을 구현하는 편이 좋다고 한다. 참고로 Serializable 인터페이스는 serialVersionUID가 선언되어있어야 한다.