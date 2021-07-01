package com.company;

public class Circle extends Figure {
    Dot dot1;
    Dot dot2;

    public Circle(Dot dot1, Dot dot2) {
        this.dot1 = dot1;
        this.dot2 = dot2;
    }

    public double calculateRadius() {
        return dot1.calculateDistanceTo(dot2);
    }

    @Override
    public double calculatePerimeter(){
        return 2 * Math.PI * calculateRadius();
    }

    @Override
    public double calculateSquare(){
        return Math.PI * Math.pow(calculateRadius(),2);
    }
}
