package com.company;

public class Triangle extends ConvexPolygon {

    public Triangle(Dot dot1, Dot dot2, Dot dot3)
    {
        super(dot1, dot2, dot3);
    }

    public double calculateMedianFrom(int dotNumber){
        Dot a = dots[dotNumber];
        Dot b = dots[(dotNumber + 1) % 3];
        Dot c = dots[(dotNumber + 2) % 3];

        double midX = (b.x + c.x)/2;
        double midY = (b.y + c.y)/2;
        Dot midDot = new Dot(midX, midY);

        return a.calculateDistanceTo(midDot);
    }

    public double calculateBisector(int dotNumber){
        double a = dots[dotNumber].calculateDistanceTo(dots[(dotNumber + 1) % 3]);
        double b = dots[(dotNumber + 1) % 3].calculateDistanceTo(dots[(dotNumber + 2) % 3]);
        double c = dots[dotNumber].calculateDistanceTo(dots[(dotNumber + 2) % 3]);
        double p = (a + b + c)/2;

        return 2 * Math.sqrt(a * c * p * (p - b))/(a + c);
    }
}
