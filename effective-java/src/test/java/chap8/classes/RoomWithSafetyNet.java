package chap8.classes;

import java.lang.ref.Cleaner;

public class RoomWithSafetyNet implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();

    // 회수해야 할 자원 numJunkPiles를 가지는 class
    private static class State implements Runnable {
        int numJunkPiles;

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("방청소");
            numJunkPiles = 0;
        }
    }

    private final State state;
    private final Cleaner.Cleanable cleanable;

    public RoomWithSafetyNet() {
        int numJunkPiles = 5;
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this,state);
    }

    @Override
    public void close() throws Exception {
        System.out.println("close 호출");
        cleanable.clean();
    }
}
