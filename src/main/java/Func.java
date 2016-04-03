import static java.lang.Math.*;

public class Func {
    Complex result;

    
    public static int getRect(double x){
        if(abs(x)<=2)
            return 1;
        else
            return 0;
    }
    
    public static double getSinc(double x){
        return sin(2*x)/(2*x);
    }
    public static double getSin(double x){
        return sin(4*Math.PI*x);
    }
    public static Complex getRadFunc(double r){
        if(r<1.0/24.0)
            return new Complex(0,0);
        else
        if(r>=1.0/24.0&&r<=1.0/2.0) {
            return new Complex(r, 0);
        }
        else

        if(r>1.0/2.0&&r<=1.0)
            return new Complex(0,Math.pow(r,2));
        else
        if(r>1)
            return new Complex(0,0);
        else return null;



    }

    

    
}
