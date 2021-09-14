package Item10.classes;

public class SmellPoint extends Point{
    private final String color;

    public SmellPoint(int x,int y, String color){
        super(x,y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Point)) return false;
        if(!(o instanceof SmellPoint)) return o.equals(this);      // Point형이면 색상을 무시하고 비교
        return super.equals(o) && ((SmellPoint) o).color == color; // ColorPoint형이면 색상까지 비교
    }
}
