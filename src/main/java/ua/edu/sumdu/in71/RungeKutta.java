package ua.edu.sumdu.in71;

import javafx.geometry.Point2D;

import java.util.function.BiFunction;

public class RungeKutta {

    public static Point2D [] getSolution(BiFunction<Double, Double, Double> derivative, double from, double to, double eps, double h, double x0, double y0) {
        int amOfPoints = (int) ((to - from) / h) + 1;
        Point2D [] points = new Point2D[amOfPoints];
        double [] X = new double[amOfPoints];
        double [] Y1 = new double[amOfPoints];
        double [] Y2 = new double[amOfPoints];
        double [] Y3 = new double[amOfPoints];
        double [] Y4 = new double[amOfPoints];
        double [] Y = new double[amOfPoints];

        double [] Y1_h = new double[amOfPoints];
        double [] Y2_h = new double[amOfPoints];
        double [] Y3_h = new double[amOfPoints];
        double [] Y4_h = new double[amOfPoints];
        double [] Y_h = new double[amOfPoints];
        for (int i = 0; i < X.length; i++) {
            X[i] = from + i * h;
        }
        X[0] = x0;
        Y[0] = Y_h[0] = y0;
        for (int i = 1; i < amOfPoints; i++) {
            double currentH = h;
            boolean isFirstIteration = true;
            do {
                if (!isFirstIteration) {
                    currentH /= 2;
                }
                //System.out.println("current h = " + currentH);
                Y1[i] = y1(derivative, X[i - 1], Y[i - 1], currentH);
                Y2[i] = y2(derivative, X[i - 1], Y[i - 1], currentH, Y1[i]);
                Y3[i] = y3(derivative, X[i - 1], Y[i - 1], currentH, Y2[i]);
                Y4[i] = y4(derivative, X[i - 1], Y[i - 1], currentH, Y3[i]);
                Y[i] = y(Y[i - 1], Y1[i], Y2[i], Y3[i], Y4[i]);

                Y1_h[i] = y1(derivative, X[i - 1], Y[i - 1], currentH / 2);
                Y2_h[i] = y2(derivative, X[i - 1], Y[i - 1], currentH / 2, Y1_h[i]);
                Y3_h[i] = y3(derivative, X[i - 1], Y[i - 1], currentH / 2, Y2_h[i]);
                Y4_h[i] = y4(derivative, X[i - 1], Y[i - 1], currentH / 2, Y3_h[i]);
                Y_h[i] = y(Y[i - 1], Y1_h[i], Y2_h[i], Y3_h[i], Y4_h[i]);
                System.out.println("point " + (i + 1) + " y1 = " + Y1[i] + " ; y2 = " + Y2[i] + " ; y3 = " + Y3[i] + " ; y4 = " + Y4[i] + "; y = " + Y[i]);
                System.out.println("Abs(" + Y[i] + " - " +  Y_h[i] + ") / 15 = " + Math.abs(Y[i] - Y_h[i]) / 15 );
                isFirstIteration = false;
            } while (Math.abs(Y[i] - Y_h[i]) / 15 > eps);
            h = currentH;
            Y[i] = Y_h[i];
            System.out.println("point " + (i + 1) + " y = " + Y[i]);
            System.out.println('\n');
        }
        for (int i = 0; i < X.length; i++) {
            points[i] = new Point2D(X[i], Y[i]);
        }
        return points;
    }

    private static double y1(BiFunction<Double, Double, Double> derivative, double x_previous, double y_previous, double h /*step*/) {
        return h * derivative.apply(x_previous, y_previous);
    }

    private static double y2(BiFunction<Double, Double, Double> derivative, double x_previous, double y_previous, double h /*step*/, double y1_current) {
        double x = x_previous + h / 2;
        double y = y_previous + y1_current / 2;
        return h * derivative.apply(x, y);
    }

    private static double y3(BiFunction<Double, Double, Double> derivative, double x_previous, double y_previous, double h /*step*/, double y2_current) {
        double x = x_previous + h / 2;
        double y = y_previous + y2_current / 2;
        return h * derivative.apply(x, y);
    }

    private static double y4(BiFunction<Double, Double, Double> derivative, double x_previous, double y_previous, double h /*step*/, double y3_current) {
        double x = x_previous + h;
        double y = y_previous + y3_current;
        return h * derivative.apply(x, y);
    }

    private static double y(double y_previous, double y1_current, double y2_current, double y3_current, double y4_current) {
        return y_previous + (y1_current + 2 * y2_current + 2 * y3_current + y4_current) / 6D;
    }
}
