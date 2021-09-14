package Item10.classes;

public class ColorPoint  extends Point {
    private final String color;

    public ColorPoint(int x,int y, String color){
        super(x,y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ColorPoint)) return false;                  //대칭성 (타입을 지킨다)
        return super.equals(o) && ((ColorPoint) o).color == color;    //대칭성 위배
    }
}
