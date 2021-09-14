package Item10;

import Item10.classes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 핵심:
 * 구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다.
 * instanceof를 getClass 검사로 바꿔도 마찬가지.
 *
 * 괜찮은 우회방법: 상속대신 컴포지션을 사용하라(Item 18) //필드에 구체클래스 추가
 */
@DisplayName("equals는 일반 규약을 지켜 재정의하라")
public class Hanui {

    @DisplayName("String의 equals는 CaseInsensitiveString을 모른다(대칭성 위배)")
    @Test
	public void test1(){
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        List<CaseInsensitiveString> list = new ArrayList<>();

        assertThat(cis).isEqualTo(s);
        assertThat(s).isNotEqualTo(cis); //String의 equals는 CaseInsensitiveString을 모른다.(대칭성)
    }

    @DisplayName("equals 규약을 어기면 컬렉션 등의 객체가 어떻게 반응할지 알 수 없다.")
    @Test
    public void test1_1(){
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        List<CaseInsensitiveString> list = new ArrayList<>();

        list.add(cis);

        assertThat(list.contains(s)).isFalse();
    }

    @DisplayName("하위 클래스에 새 필드 추가(대칭성 위배)")
    @Test
    public void test2(){
        Point p = new Point(1,2);
        ColorPoint cp = new ColorPoint(1,2, "RED");

        assertThat(p).isEqualTo(cp);        //위치(x,y)만 비교
        assertThat(cp).isNotEqualTo(p);     //ColorPoint는 Point와 타입을 배제한다.(대칭성)
    }

    @DisplayName("하위 클래스에 새 필드 추가(추이성 위배)")
    @Test
    public void test3(){
        ColorPoint2 cp = new ColorPoint2(1,2,"RED");
        Point p = new Point(1,2);
        ColorPoint2 cp2 = new ColorPoint2(1,2,"BLUE");

        assertThat(cp).isEqualTo(p);        //cp == p
        assertThat(p).isEqualTo(cp2);       //p == cp2
        assertThat(cp).isNotEqualTo(cp2);   //cp != cp2 (추이성)
    }

    @DisplayName("하위 클래스에 새 필드 추가(StackOverflowError 발생 위험)")
    @Test
    public void test4(){
        ColorPoint2 cp = new ColorPoint2(1,2,"RED");
        SmellPoint sp = new SmellPoint(1,2,"RED");  //서로 equals를 미룬다

        Assertions.assertThrows(StackOverflowError.class, () -> cp.equals(sp));
    }


    @DisplayName("하위 클래스에 새 필드 추가, getClass 검사로 변경(컬렉션 응답 달라짐)")
    @Test
    public void test5(){
        PointWithGetClass pwc = new PointWithGetClass(1,2);
        CounterPoint cp = new CounterPoint(1,2);
        List<PointWithGetClass> list = new ArrayList<>();

        list.add(pwc);

        assertThat(list.contains(cp)).isFalse(); //equals에서 getClass에 의해 배제됨
    }

    @DisplayName("TimeStamp와 Date를 equals로 비교하는 상황은 만들면 안된다.")
    @Test
    public void test6(){
        Timestamp t1 = new Timestamp(2021,9,15,2,3,0,0);
        Date t2 = new Date(2021,9,15,2,3,0);
        DateSubNoMoreFields t3 = new DateSubNoMoreFields(2021,9,15,2,3,0);
        List<Date> list = new ArrayList<>();

        list.add(t2);

        assertThat(list.contains(t1)).isFalse();
        assertThat(list.contains(t3)).isTrue(); //서브클래스에 필드 추가 안 했다면 contains 수행가능하다.
    }
}
