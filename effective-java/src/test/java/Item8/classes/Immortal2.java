package Item8.classes;

public class Immortal2 {
    public static Immortal2 immortal2;
    public int amount;

    // finalize에 의해 생성자의 유효성 검사가 무효화 됨
    public Immortal2(boolean isValid) {
        if (!isValid) throw new NullPointerException("NPE 발생");
        amount = 1000;
    }

    protected void finalize() {
        System.out.println("finalize 실행");
        immortal2 = this;
    }
}
