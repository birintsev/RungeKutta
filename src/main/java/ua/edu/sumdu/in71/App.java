package ua.edu.sumdu.in71;

import javafx.geometry.Point2D;
import java.util.function.BiFunction;

public class App {
    public static void main(String[] args) {
        BiFunction<Double, Double, Double> derivative = (x, y) -> Math.pow(x, 2) - x * y + Math.pow(y, 2);
        double x0 = 0;
        double y0 = 0.1;
        double from = 0;
        double to = 1;
        double step = 0.1;
        double eps = 1e-5;
        Point2D [] result = RungeKutta.getSolution(derivative, from, to, eps, step, x0, y0);
        for (Point2D point2D : result) {
            System.out.println(point2D);
        }
    }
}
