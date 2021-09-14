package Item8;

import Item8.classes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * finalizer와 cleaner는 느리고 예측할 수 없으므로 테스트 코드를 작성했을 때
 * 결과가 일관적이지 않을 수 있다.
 *
 * @see System#gc()
 * finalize 수행을 위해 일부러 gc를 부른다.
 * 단, 이 작업은 강제적 또는 즉시 수행하진 않는다.
 * 따라서 로그의 순서가 달라질 수 있으나, 이게 최선이다.
 * 참고 : https://stackoverflow.com/questions/1481178/how-to-force-garbage-collection-in-java
 */
@DisplayName("Item8. finalizer와 cleaner 사용을 피하라")
public class Hanui {

    @DisplayName("finalizer에서 발생한 예외는 무시된다.")
    @Test
    public void test0() {
        Foo foo = new Foo();
        foo = null;

        System.gc();
        System.out.println("예외는 무시됩니다.");
    }

    @DisplayName("finalizer(cleaner)는 심각한 성능저하가 있다.")
    @Test
    public void test1() throws Exception {
        // try-with-resources 사용
        long start = System.nanoTime();
        try (RoomWithAutoCloseable roomWithAutoCloseable = new RoomWithAutoCloseable()) {
            //AutoCloseable 구현에 의해 자동으로 닫아짐
        }
        long end = System.nanoTime();
        System.out.println("try-with-resources 사용: " + (end - start) / 1000000.0 + "ms");

        // finalizer 사용
        start = System.nanoTime();
        RoomWithFinalizer roomWithFinalizer = new RoomWithFinalizer();
        roomWithFinalizer = null;
        System.gc();
        end = System.nanoTime();
        System.out.println("finalizer 사용: " + (end - start) / 1000000.0 + "ms");

        // 안전망 설치
        start = System.nanoTime();
        try(RoomWithSafetyNet roomWithSafetyNet = new RoomWithSafetyNet()){

        }
        end = System.nanoTime();
        System.out.println("안전망 설치(try-with-resources 사용): " + (end - start) / 1000000.0 + "ms");
    }

    // 참고 : https://yangbongsoo.tistory.com/8
    @DisplayName("finalize공격: 정적필드에 자기 참조하면 gc되지 않는다.")
    @Test
    public void test2() {
        Normal beCleaned = new Normal();
        Immortal notBeCleaned = new Immortal();

        beCleaned = null;
        notBeCleaned = null;
        System.gc();

        assertThat(beCleaned).isNull();
        assertThat(notBeCleaned).isNull();
        assertThat(notBeCleaned.immortal).isNotNull();
    }

    @DisplayName("finalize공격: 생성자에서 예외 발생해도 gc되지 않는다.")
    @Test
    public void test2_2() {
        Immortal2 immortal2 = null;

        try {
            immortal2 = new Immortal2(false);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.gc();
            Thread.sleep(1000); //gc에 충분한 시간을 주기 위해
            assertThat(immortal2.immortal2).isNotNull();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @DisplayName("finalize공격: 생성자에서 예외 발생해도 하위 클래스에서 finalizer 수행된다.")
    @Test
    public void test2_3() {
        Immortal3Extension ex;
        try{
            new Immortal3Extension(false); // NPE occur!
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        ex=null;
        try {
            System.gc(); //finalizer 호출
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(ex.immortal3).isNotNull();
        assertThat(ex.immortal3.amount).isEqualTo(0); // 부모 생성자 실패로 초기값인 0유지
    }
}