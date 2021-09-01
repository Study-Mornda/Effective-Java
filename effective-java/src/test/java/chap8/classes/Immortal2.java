package chap8.classes;

public class ImmortalEvenConstructorException {
    public static ImmortalEvenConstructorException immortal2;

    // finalize에 의해 생성자의 유효성 검사가 무효화 됨
    public ImmortalEvenConstructorException(boolean isValid) {
        if (!isValid) throw new NullPointerException("NPE 발생");
    }

    public void finalize() {
        System.out.println("finalize 실행");
        immortal2 = this;
    }
}
