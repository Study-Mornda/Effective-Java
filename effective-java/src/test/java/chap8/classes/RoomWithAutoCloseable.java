package chap8.classes;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {

    protected void finalize(){
        System.out.println("finalize 호출");
    }

    /**
     * AutoCloseable#close덕분에 try-with-resources를 사용해
     * 자동으로 자원을 닫도록 한다.
     */
    @Override
    public void close() throws Exception {
        System.out.println("close 호출");
    }
}
