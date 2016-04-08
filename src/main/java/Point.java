/**
 * Created by Sasha on 12.03.2016.
 */
public class Point {
    private double x;
    private double y;
    private Complex radFunc;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, Complex radFunc) {
        this.x = x;
        this.radFunc = radFunc;
    }
    public Complex getRadFunc(){
        return this.radFunc;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
