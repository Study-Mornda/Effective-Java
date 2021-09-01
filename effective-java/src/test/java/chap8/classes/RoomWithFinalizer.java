package chap8.classes;

public class RoomWithFinalizer {

    protected void finalize(){
        System.out.println("finalize 호출");
    }
}
