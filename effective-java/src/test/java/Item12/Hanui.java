package Item12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("toString을 항상 재정의하라")
public class Hanui {

    @DisplayName("toString 반환값의 포맷을 명시한다.")
    @Test
    public void test1(){
        PhoneNumber pn = new PhoneNumber(11,333,333);
        assertThat(pn.toString()).isEqualTo("011-333-0333");
    }
}
