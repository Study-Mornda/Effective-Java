package Item14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comparable을 구현할지 고려하라")
public class Hanui {

    @DisplayName("BigDecimal은 equals로 비교하면 다른 것들이 있다.")
    @Test
    public void test2(){
        BigDecimal bc = new BigDecimal("1.0");
        BigDecimal bc2 = new BigDecimal("1.00");
        boolean a = bc.equals(bc2);
        System.out.println(a);
    }

    @DisplayName("정렬된 컬렉션들은 동치성 비교 시 equals 대신 compareTo를 사용한다.")
    @Test
    public void test1(){
        BigDecimal bd = new BigDecimal("1.0");
        BigDecimal bd2 = new BigDecimal("1.00");
        Set<BigDecimal> hset = new HashSet<>();
        Set<BigDecimal> tset = new TreeSet<>(); //정렬된 컬렉션

        hset.add(bd);
        hset.add(bd2);
        tset.add(bd);
        tset.add(bd2);

        assertThat(hset.size()).isEqualTo(2);//원소를 두 개 갖는다.
        assertThat(tset.size()).isEqualTo(1);//원소를 하나 갖는다.
        hset.stream().forEach(System.out::println);
        System.out.println();
        tset.stream().forEach(System.out::println);
    }



}
