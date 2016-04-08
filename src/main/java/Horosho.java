import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sasha on 02.04.2016.
 */
public class Horosho {
    public static Complex radFunc(double r){
        return Func.getRadFuncMy(r);

    }
    public static void main(String[] args) {
        Chart2D chart = new Chart2D();
        ITrace2D trace = new Trace2DSimple();
        trace.setColor(Color.BLUE);
        //ITrace2D trace2 = new Trace2DSimple();
        //trace2.setColor(Color.BLUE);
        chart.addTrace(trace);
        //chart.addTrace(trace2);
        java.util.List<Point> pointsArg = new ArrayList<Point>(),pointsModule = new ArrayList<Point>(),points = new ArrayList<Point>();
        double maxModule=0;
        double x=0;
        Complex matrica[][] = new Complex[2001][2001];
        for(int i = 0;i<1000;i++){
            x=0+0.001*i;
            try {
                if(maxModule<Math.sqrt(Math.pow(radFunc(x).dReal, 2) + Math.pow(radFunc(x).dImaginary, 2)))
                    maxModule=Math.sqrt(Math.pow(radFunc(x).dReal, 2) + Math.pow(radFunc(x).dImaginary, 2));
                pointsModule.add(new Point(x,
                        Math.sqrt(Math.pow(radFunc(x).dReal, 2) + Math.pow(radFunc(x).dImaginary, 2))));
                pointsArg.add(new Point(x,
                        radFunc(x).getArg()));
                points.add(new Point(x,radFunc(x)));
                //System.out.println(radFunc(x).toString());
                trace.addPoint(x,
                        Math.sqrt(Math.pow(radFunc(x).dReal, 2) + Math.pow(radFunc(x).dImaginary, 2)));
                //trace2.addPoint(x, radFunc(x).getArg());
                //System.out.println(Func.getRadFunc(x).getArg());
            }
            catch (NullPointerException e){
                System.out.println("asddasasd");

            }
        }
        System.out.println(maxModule);
        double colors[][] = new double[2001][2001];
        int alpha;
        for(int j =0;j<2001;j++){
            for (int k = 0;k<2001;k++){
                alpha=(int)Math.rint(Math.sqrt(Math.pow(j-1000,2)+Math.pow(k-1000,2)));
                if(alpha>=1000){
                    matrica[j][k]=new Complex(0,0);
                }

                else
                {
                //    matrica[j][k]=points.get(alpha).getRadFunc();
                //}

                matrica[j][k]=points.get(alpha).getRadFunc();
                    //System.out.println(matrica[j][k]);
                }
                //colors[j][k] = Math.sqrt(Math.pow(matrica[j][k].dReal, 2) + Math.pow(matrica[j][k].dImaginary,2)) * 255 / maxModule;
                double t = (Math.atan2(matrica[j][k].dImaginary, matrica[j][k].dReal));
                if (t >= 0) {
                    colors[j][k] = t * 255 / Math.PI / 2;
                } else {
                    colors[j][k] = (t + 2 * Math.PI) * 255 / Math.PI / 2;
                }
                //System.out.println(colors[j][k]);
            }
        }


        try {
            ImageIO.write(createImage(colors), "jpg", new File("module1.jpg"));
        } catch (IOException e) {
            System.out.println("Hi!");
        }
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
    public static BufferedImage createImage(double[][] colors) {
        int rowCount = colors.length;
        int columnCount = colors[0].length;
        Color[][] image = new Color[rowCount][columnCount];

        for (int j = 0; j < rowCount; j++) {
            for (int k = 0; k < columnCount; k++) {
                image[j][k] = new Color((int) colors[j][k], (int) colors[j][k], (int) colors[j][k]);
            }
        }

        BufferedImage bufferedImage = new BufferedImage(rowCount, columnCount, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                bufferedImage.setRGB(x, y, image[x][y].getRGB());
            }
        }

        return bufferedImage;
    }
}
