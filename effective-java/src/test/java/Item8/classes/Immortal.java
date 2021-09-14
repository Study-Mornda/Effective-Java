package Item8.classes;

public class Immortal {
    public static Immortal immortal;

    // finalizer에 의해 gc 대상에서 벗어난다
    public void finalize() {
        this.immortal = this;
    }
}
