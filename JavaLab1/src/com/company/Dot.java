package com.company;

public class Dot {
    public double x;
    public double y;

    public Dot(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    double calculateDistanceTo(Dot dot){
        return Math.sqrt(Math.pow(this.x - dot.x,2) + Math.pow(this.y - dot.y,2));
    }

    double calculateDistanceTo(Line line)
    {
        double a = this.calculateDistanceTo(line.dot1);
        double b = line.dot1.calculateDistanceTo(line.dot2);

        double cos = ((line.dot2.x - line.dot1.x) * (x - line.dot1.x) + (line.dot2.y - line.dot1.y) * (y - line.dot1.y)) / (a * b);
        double sin = Math.sqrt(1 - Math.pow(cos, 2));

        return a * sin;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
