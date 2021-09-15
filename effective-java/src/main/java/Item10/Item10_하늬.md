# Item10. equals는 일반 규약을 지켜 재정의하라

equals 메서드를 재정의하지 않는 경우는 (`웬만하면 하지 마라`)
- 각 인스턴스가 본질적으로 고유할 때 (ex. `Thread`처럼 동작하는 개체)
- 인스턴스의 '논리적 동치성'을 검사할 일이 없을 때
  - 설계자에 의해 클라이언트가 equals를 재정의하지 않는걸 원하거나 필요하지 않다고 판단할 경우 
     `Object`의 equals만으로도 해결된다. 
- 상의 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞을 때
    - `Set` 구현체는 `AbstractSet`이 구현한 equals를 상속받아 쓴다.
- 클래스가 private이거나 package-private이고 equals 메서드를 호출할 일이 없을 때
  - 더 완벽히 호출을 막고 싶으면 아래처럼 구현한다.
     ```java
       @Override public boolean equals(Object o){
         throw new AssertionError(); // 호출 금지
       }
     ```
    
equals를 재정의하는 경우는
- 논리적 동치성을 확인해야 하는데, 상위 클래스의 equals가 재정의되지 않아서 논리적 동치성을 비교할 수 없을 때
  (ex. `Integer`, `String` 등의 값 클래스)
    - 값 클래스라 해도, 값이 같은 인스턴스가 두 개 이상 만들어지지 않으면 재정의하지 않아도 된다.
      (ex. `Enum`)

equals를 재정의하려면 **일반 규약**을 따라야한다.
```yaml
equals 메서드는 동치관계를 구현하며 다음을 만족한다.
- 반사성(reflexivity): null이 아닌 모든 참조 값 x에 대해, x.equals(x)는 true다.
- 대칭성(symmetry): null이 아닌 모든 참조 값 x,y에 대해 x.equals(y)가 true면 
  y,equals(x)도 true다
- 추이성(transivity): null이 아닌 모든 참조 값 x,y,z에 대해 x.equals(y)가 
  true이고 y.equals(z)도 true면
  x.equals(z)도 true다.
- 일관성(consistency): null이 아닌 모든 참조 값 x,y에 대해, x.equals(y)를 
  반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.
- null-아님: null이 아닌 모든 참조 값 x에 대해 x.equals(null)은 false다.
```

규약 위배의 위험성

- `컬렉션 클래스`들을 포함한 수많은 클래스들은 전달받은 객체가 equals 규약을 지킨다고 가정하고 동작한다. 
    
  => **즉, equals 규약을 위배하면 기본적으로 컬렉션 클래스와 제대로 상호작용이 안될 수 있다.**

규약 설명 (테스트 코드 참고)

- 반사성
  - **나는 나.. (일부러 어기기 어렵다)**
- 대칭성
  - **나는 너, 너는 나(자칫 어길 수 있다.)**
  - equals에 다른`값 클래스`와 연동하겠다는 허황된 꿈을 버려라(커스텀한 클래스는 `값 클래스`를 알지만, `값 클래스`는 커스텀한 클래스를 모르니까)
  - 대신 커스텀한 클래스와 같은 클래스일 때만 `equals`비교해라
- 추이성
  - **내가 너고, 너가 얘면, 나도 얘다(자칫 어길 수 있다.)**
  - 상위 클래스에는 없는 새로운 필드를 하위 클래스에 추가하는 상황에 어길 수 있음
  - 자식 클래스끼리 equals비교시 자칫 무한 재귀에 빠져서 `StackOverflowError`를 일으킬 수 있다.
  - 애초에 **구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다.**(`instanceof`대신 `getClass`검사로 바꿔도 똑같다)
  - 대안 (Item18. 상속 대신 컴포지션을 사용하라):
    1. Point(구체클래스)를 상속하는 대신 Point를 ColorPoint(서브클래스)의 private 필드로 두고
    2. ColorPoint와 같은 위치의 일반 Point를 반환하는 뷰(view) 메서드를 public으로 추가한다.
- 일관성
  - 두 객체가 같다면(둘 중 하나 or 둘 다 수정되지 않는 한) 앞으로도 영원히 같아야 한다.
  - 가변 객체는 비교 시점에 따라 서로 다를/같은 수도 있자만, 불변 객체는 한번 다르/같으면 유지돼야한다.
  - 가변이든 불변이든 equals 비교시 신뢰할 수 없는(매번 바뀌는 등) 자원이 끼어들면 안된다.
- null아님
  - 모든 객체가 null과 같지 않아야한다.
  - equals에서 `NPE`조차 던지지 말아야한다. 그냥 `if(!(o instanceof MyType))`으로 묵시적으로 null검사까지 되게
    하는게 낫다.

equals메서드 구현 방법 정리
1. `==`연산자로 입력이 자기 자신의 참조인지 확인한다. (성능 최적화용)
2. instanceof 연산자로 입력이 올바른 타입인지 확인한다.
3. 입력을 올바른 타입으로 형변환한다.
4. 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사한다.
    - 필드 비교 타입
      ```yaml
      기본타입 필드(float, double제외) -> ==
      참조 타입 필드 -> 각각 equals
      float, double -> Float.compare(float, float), Double.compare(double, double)
      ```
      (float, double은 Float.NaN, -0.0f, 특수한 부동소수 값 등을 다뤄야하므로)
   
    - 필드 비교 순서(성능)  
       - 다를 가능성이 더 크거나 비교하는 비용이 더 싼 필드 우선.
   
       - 전체의 상태를 대표한다면 파생 필드를 비교하는 쪽이 더 빠를 수 있다.  

마지막 주의사항
- equals를 재정의할 땐 hashCode도 반드시 재정의하자
  - 구글의 `AutoValue` 프레임워크로 자동으로 equals, hashCode를 작성할 수 있다.
- 너무 복잡하게 해결하려 들지 말자.
- Object외 타입을 매개변수로 받지 말아야한다.