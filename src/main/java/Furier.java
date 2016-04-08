/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Furier {
    Complex result;
    List<Point> module = new ArrayList<Point>(),
            argument = new ArrayList<Point>(), analiticModule = new ArrayList<Point>(),analiticArg =new ArrayList<Point>();
    List<Complex> resultFurie=new ArrayList<Complex>(),resultObr = new ArrayList<Complex>();
    List<Point> moduleObr = new ArrayList<Point>(),
            argumentObr = new ArrayList<Point>(), analiticModuleObr = new ArrayList<Point>(),ishFunc = new ArrayList<Point>();

    public List<Point> getIshFunc() {
        return ishFunc;
    }

    public List<Point> getModuleObr() {
        return moduleObr;
    }

    public List<Point> getArgumentObr() {
        return argumentObr;
    }

    public List<Point> getAnaliticModuleObr() {
        return analiticModuleObr;
    }

    public List<Point> getAnaliticModule() {

        return analiticModule;
    }

    public List<Point> getModule() {
        return module;
    }

    public void setModule(List<Point> module) {
        this.module = module;
    }

    public List<Point> getArgument() {
        return argument;
    }

    public void setArgument(List<Point> argument) {
        this.argument = argument;
    }

    public Furier(int n) {
        //setFurier(n);
        calcFurier(n);
        calcFurierObr(n);
    }

    public static int getRect(double x) {
        if (abs(x) <= 2)
            return 1;
        else
            return 0;
    }

    public static double getSin(double x) {
        return sin(4 * Math.PI * x);
    }
    public static double getSinMin(double x) {
        return sin(-4 * Math.PI * x);
    }
    public static double getSin0() {
        return sin(0);
    }

    public List<Point> getAnaliticArg() {
        return analiticArg;
    }

    public Complex getFurier(double k, double l, double h){
        return new Complex(getRect(k) * getSin(k) * cos(2 * Math.PI * l * k)*h,- getRect(k) * getSin(k) * sin(2 * Math.PI * l * k)*h);
    }

    public Complex getFurierObr(Complex F,double xk,double ul,double h){

        return new Complex((F.dReal*cos(2 * Math.PI * ul * xk))*h,
                F.dImaginary*Math.sin(2 * Math.PI * ul * xk)*h);
    }
    public void calcFurier(int n) {
        long timeout = System.currentTimeMillis();

        double q = 10.0 / n;
        Complex res = new Complex(0, 0);
        for(int l = 0; l < 10000; l += 1){
            double ul = -5.0+0.001*l;
            Complex subsum = new Complex(0, 0);
            for (int k = 0; k < n; k += 1) {
                double xk = -5+q*k;
                Complex gap = getFurier(xk,ul,q);

                subsum = subsum.Add(gap);

            }
            ishFunc.add(new Point(ul,getRect(ul) * getSin(ul)));

            module.add(new Point(ul, (Math.sqrt(pow(subsum.dReal, 2) + pow(subsum.dImaginary, 2)))));
            //System.out.println(ul+"  "+Math.sqrt(pow(subsum.dReal, 2) + pow(subsum.dImaginary, 2)));
            argument.add(new Point(ul, subsum.getArg()));
            analiticModule.add(new Point(ul, Math.abs(getAnal(ul).dImaginary)));
            analiticArg.add(new Point(ul,getAnal(ul).getArg()));

            resultFurie.add(subsum);
        }

        timeout = System.currentTimeMillis() - timeout;
        double timeoutD = timeout;
        System.out.println("Время выполнения для n= " + n + " :" + timeoutD / 1000.0 + " секунд"+" Eps1 = "+getEps1(n,0)+" Eps2 = "+getEps2(n,0)+" Eps3 = "+getEps3(n,0));

        this.result = res;
    }
    public void calcFurierObr(int n) {
        double h = 10.0 / n;
        for (int k = 0; k < n; k += 1) {
            double xk = -5+h*k;

            Complex subsum = new Complex(0, 0);
            for(int l = 0; l < 10000; l += 1){
                double ul = -5+0.001*l;
                Complex tmp = getFurierObr(resultFurie.get(l),xk,ul,h);

                subsum = subsum.Add(tmp);

            }
            moduleObr.add(new Point(xk, (Math.sqrt(pow(subsum.dReal, 2) + pow(subsum.dImaginary, 2)))));
            argumentObr.add(new Point(k, subsum.getArg()));
            System.out.println("Время выполнения для n= " + n + " :" + " секунд"+" Eps1 = "+getEps1Obr(n,0)+" Eps2 = "+getEps2Obr(n,0)+" Eps3 = "+getEps3Obr(n,0));

        }
    }
    public Complex getAnal(double u) {
        if(Math.abs(u)!=2) {
            return new Complex(0, (-2 * Math.sin(4 * Math.PI * u)) / (Math.PI * (pow(u, 2) - 4)));
        }
        else

            return new Complex(0, (-2 * Math.sin(4 * Math.PI * u+0.01)) / (Math.PI * (pow(u+0.01, 2) - 4)));

    }
    public double getEps1(int n,int mode){
        double res=0;
        for (double l = -5; l <= 5; l += 0.001) {
            Complex subsum = new Complex(0, 0);
            for (double k = -5; k <= 5; k += 10.0/n) {
                Complex gap;
                //if (mode==1)
                //gap = getFurierObr(k,l,10.0/n);
                //else
                gap = getFurier(k,l,10.0/n);
                subsum = subsum.Add(gap);

            }
            res += (subsum.Sub(getAnal(l)).getModel());
        }
        return res/10000;
    }
    public double getEps2(int n,int mode){
        double res=0;
        for (double l = -5; l <= 5; l += 0.001) {
            Complex subsum = new Complex(0, 0);
            for (double k = -5; k <= 5; k += 10.0/n) {
                Complex gap;
                //if (mode==1)
                //    gap = getFurierObr(k,l,10.0/n);
                // else
                gap = getFurier(k,l,10.0/n);
                subsum = subsum.Add(gap);


            }
            res += pow((subsum.Sub(getAnal(l)).getModel()),2);
        }
        return sqrt(res/10000);

    }
    public double getEps3(int n,int mode ){
        double res=0;
        for (double l = -5; l <= 5; l += 0.001) {
            Complex subsum = new Complex(0, 0);
            for (double k = -5; k <= 5; k += 10.0/n) {
                Complex gap;
                //if (mode==1)
                //    gap = getFurierObr(k,l,10.0/n);
                //else
                gap = getFurier(k,l,10.0/n);
                subsum = subsum.Add(gap);

            }
            if(res<subsum.Sub(getAnal(l)).getModel())
                res = (subsum.Sub(getAnal(l)).getModel());
        }
        return res;


    }

    public double getEps1Obr(int n,int mode){
        double h = 10.0 / n;
        double res=0;
        for (int k = 0; k < n; k += 1) {
            double xk = -5+h*k;

            Complex subsum = new Complex(0, 0);
            for(int l = 0; l < 10000; l += 1){
                double ul = -5+0.001*l;
                Complex tmp = getFurierObr(resultFurie.get(l),xk,ul,h);

                subsum = subsum.Add(tmp);

            }
            res += (subsum.getModel()-ishFunc.get(k).getY());
        }
        return res/10000;
    }
    public double getEps2Obr(int n,int mode){
        double h = 10.0 / n;
        double res=0;
        for (int k = 0; k < n; k += 1) {
            double xk = -5+h*k;

            Complex subsum = new Complex(0, 0);
            for(int l = 0; l < 10000; l += 1){
                double ul = -5+0.001*l;
                Complex tmp = getFurierObr(resultFurie.get(l),xk,ul,h);

                subsum = subsum.Add(tmp);

            }
            res += pow(subsum.getModel()-ishFunc.get(k).getY(),2);
        }
        return sqrt(res/10000);

    }
    public double getEps3Obr(int n,int mode ){
        double h = 10.0 / n;
        double res=0;
        for (int k = 0; k < n; k += 1) {
            double xk = -5+h*k;

            Complex subsum = new Complex(0, 0);
            for(int l = 0; l < 10000; l += 1){
                double ul = -5+0.001*l;
                Complex tmp = getFurierObr(resultFurie.get(l),xk,ul,h);

                subsum = subsum.Add(tmp);

            }
            if(res<subsum.getModel()-ishFunc.get(k).getY())
                res = (subsum.getModel()-ishFunc.get(k).getY());
        }
        return res;


    }


}
