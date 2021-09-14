package Item6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

class RomanNumerals{
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD] |D?C{0,3})"
                    + "(X[CL] |L?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumeral_withCaching(String s) {
        return ROMAN.matcher(s).matches();
    }
    static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD] |D?C{0,3})"
                + "(X[CL] |L?X{0,3})(I[XV]|V?I{0,3})$");
    }
}
@DisplayName("Item6. 불필요한 객체 생성을 피하라")
public class Hanui {

    @DisplayName("new는 필요 이상의 인스턴스를 새로 생성하게된다.")
    @Test
    public void test1() {
        String s1 = new String("bikini");
        String s2 = new String("bikini");
        assertThat(s1 != s2);
    }

    @DisplayName("new 대신 문자열 리터럴은 인스턴스를 재사용할 수 있다.")
    @Test
    public void test2() {
        String s1 = "bikini";
        String s2 = "bikini";
        assertThat(s1 == s2);
    }

    @DisplayName("이미 Pool에 값이 있다면 같은 주소를 바라본다.")
    @Test
    public void test2_1(){
        String s = new String("aaa");
        String b = "aaa";
        String c = s.intern();

        System.out.println(s == b); // false
        assertThat(b == c); // true
    }

    @DisplayName("생성 비용이 아주 비싼 객체는 초기화 시 캐싱해두면 재사용이 가능하다.")
    @Test
    public void test3(){
        double start,end;
        String s = "HelloJava";
        start = System.currentTimeMillis();
        for(int i=0;i<10000;i++) {
            RomanNumerals.isRomanNumeral(s);
        }
        end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");

        start = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            RomanNumerals.isRomanNumeral_withCaching(s);
        }
        end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");
    }

    @DisplayName("Map#keySet은 Set인스턴스를 재사용해 반환하므로 가변임에도 안전하다.")
    @Test
    public void test4(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1000);
        map.put(2, 2000);
        map.put(3, 3000);
        Set<Integer> set1 = map.keySet();
        Set<Integer> set2 = map.keySet();
        assertThat(set1 == set2);
    }

    @DisplayName("오토박싱은 성능이 좋지않으므로 이유가 없다면 지양하자.")
    @Test
    public void test5(){
        double start, end;
        start = System.currentTimeMillis();
        Long sum = 0L;
        for(long i=0;i<= Integer.MAX_VALUE;i++){
            sum+=i;
        }
        end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");

        start = System.currentTimeMillis();
        long sum2 = 0L;
        for(long i=0;i<= Integer.MAX_VALUE;i++){
            sum2+=i;
        }
        end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0+"s");
    }


}
