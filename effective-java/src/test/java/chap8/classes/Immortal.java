package chap8.classes;

public class Zombi {
    public static Zombi zombi;

    public void invalidMethod() {
        System.out.println("System attacked");
    }

    public void finalize() {
        this.zombi = this; // finalizer는 정적 필드에 자신의 참조를 할당하여 gc 대상에서 벗어난다
    }
}
