package Item10.classes;

public class PointWithGetClass {
    private int x, y;

    public PointWithGetClass(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){

        if(o == null || o.getClass() !=getClass()) {
            System.out.println(o.getClass());
            return false;
        }
        PointWithGetClass p = (PointWithGetClass) o;
        return p.x == x && p.y == y;
    }
}
