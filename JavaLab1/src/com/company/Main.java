package com.company;

public class Main {
    public static void main(String[] args)
    {
        Parallelogram param = new Parallelogram(new Dot(3,0), new Dot(5,3), new Dot(2,3), new Dot(0,0));
        System.out.println("Parallelogram: ");
        showInfo(param);

        ConvexPolygon polygon = new ConvexPolygon(
                new Dot(4,-1), new Dot(2,-2), new Dot(5,0), new Dot(4,4), new Dot(2,4),
                new Dot(0,0), new Dot(0,2), new Dot(6,2)
        );
        System.out.println("Polygon: ");
        //Вывод координат полигона для проверки сортировки
        showCords(polygon);
        showInfo(polygon);

        Triangle triangle = new Triangle(new Dot(0,0), new Dot(1,2), new Dot(1,0));
        System.out.println("Triangle: ");
        double h = triangle.calculateHeight(triangle.dots[2], new Line(triangle.dots[1], triangle.dots[0]));
        System.out.println("Triangle height = " + h);
        double m = triangle.calculateMedianFrom(1);
        System.out.println("Triangle median = " + m);
        double b = triangle.calculateBisector(1);
        System.out.println("Triangle bisector = " + b);
        showInfo(triangle);

        //Запись объекта в файл
        triangle.toFile();

        // Сравнение фигур с помощью метода, имплементированного интерфейсом Comparable
        System.out.println("comparison = " + triangle.compareTo(polygon));

        // Сравнение фигур с помощью класса, реализующего интерфейс Comparator
        FigureComporator comparator = new FigureComporator();
        System.out.println("comparison = " + comparator.compare(triangle, polygon));
    }

    public static void showInfo(ConvexPolygon polygon)
    {
        System.out.println("Polygon P = " + polygon.calculatePerimeter());
        System.out.println("Polygon S = " + polygon.calculateSquare());
        for (double side : polygon.calculateSides()){
            System.out.println("Side = " + side);
        }
        System.out.println();
    }

    public static void showCords(ConvexPolygon polygon)
    {
        for (Dot dot : polygon.dots)
        {
            System.out.println("(" + dot.x + "," + dot.y + ")");
        }
    }
}
