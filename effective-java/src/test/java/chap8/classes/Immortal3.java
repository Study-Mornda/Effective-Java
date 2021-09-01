package chap8.classes;

public class Immortal3 {
    public int amount;

    public Immortal3(boolean isValid) {
        if (!isValid) throw new NullPointerException("NPE 발생");
        amount = 1000;
    }

}
