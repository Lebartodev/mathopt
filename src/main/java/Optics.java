/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Scanner;

public class Optics {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите n:");
        int n = in.nextInt();
        Furier fur = new Furier(n);
        //Исходная
        List<Point> points1 = fur.getIshFunc();

        Chart2D chart = new Chart2D();
        ITrace2D trace = new Trace2DSimple();
        trace.setColor(Color.GREEN);
        chart.addTrace(trace);
        for (Point p : points1)
            trace.addPoint(p.getX(), Math.abs(p.getY()));

        //Прямое преобразование
        /*List<Point> points2 = fur.getModule();

        ITrace2D trace2 = new Trace2DSimple();
        trace2.setColor(Color.ORANGE);
        chart.addTrace(trace2);
        for (Point p : points2)
            trace2.addPoint(p.getX(), p.getY());
            */
        //Обратное преобразование
        List<Point> points3 = fur.getModuleObr();

        ITrace2D trace3 = new Trace2DSimple();
        trace3.setColor(Color.RED);
        chart.addTrace(trace3);
        for (Point p : points3)
            trace3.addPoint(p.getX(), p.getY());





        JFrame frame = new JFrame("MinimalStaticChart");

        frame.getContentPane().add(chart);
        frame.setSize(400, 300);

        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.setVisible(true);
    }

}
    

