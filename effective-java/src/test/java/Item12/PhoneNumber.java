package Item12;

public class PhoneNumber {
    int areaCode; //지역 코드
    int prefix;   //프리픽스
    int lineNum;  //가입자 번호

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    // 포맷 명시 하든말든 toString이 반환하는 값에 포함된 정보의 접근자 만들자
    public int getAreaCode(){
        return this.areaCode;
    }
    public int getPrefix(){
        return this.prefix;
    }
    public int getLineNum(){
        return this.lineNum;
    }
    /**
     * 이 전화번호의 문자열 표현을 반환한다.
     * 이 문자열을 "XXX-YYY-ZZZZ" 형태의 12 글자로 구성된다.
     * XXX: 지역코드
     * YYY: 프리픽스
     * ZZZZ: 가입자 번호
     * 각각의 대문자는 10진수 숫자 하나를 나타낸다.
     *
     * 전화번호의 각 부분의 값이 너무 작아서 자릿수를 채울 수 없다면,
     * 앞에서부터 0으로 채워나간다. 예컨대 가입자 번호가 123이라면
     * 전화번호의 마지막 네 문자는 "0123"이 된다.
     * @return
     */
    @Override
    public String toString() {
        return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
    }

}
