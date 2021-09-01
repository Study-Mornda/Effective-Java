package chap8.classes;

public class Immortal3Extension extends Immortal3{
    public static Immortal3 immortal3;

    public Immortal3Extension(boolean isValid) {
        super(isValid);
    }

    public void finalize(){
        immortal3 = this;
    }
}
