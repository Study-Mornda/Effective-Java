package chap8.classes;

public class Foo {

    /**
     * "명시적 호출"에서는 호출을 마칠 때까지 객체에 대한 참조가 존재하므로 예외 발생.
     * 그에 반해 객체 참조가 null일 때 gc에 의해 "암묵적 호출"이 일어나므로 예외는 이를 포착할 수 없다고 함.
     */
    protected void finalize() {
        System.out.println("finalize 예외 발생");
        throw new RuntimeException();
    }
}
