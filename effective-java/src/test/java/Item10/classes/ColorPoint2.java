package Item10.classes;

public class ColorPoint2  extends Point {
    private final String color;

    public ColorPoint2(int x,int y, String color){
        super(x,y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Point)) return false;
        if(!(o instanceof ColorPoint2)) return o.equals(this);      // Point형이면 색상을 무시하고 비교
        return super.equals(o) && ((ColorPoint2) o).color == color; // ColorPoint형이면 색상까지 비교
    }
}
