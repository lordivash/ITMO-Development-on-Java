package com.company;

public class ConvexPolygon extends Figure{

    public ConvexPolygon(Dot ... dots)
    {
        this.dots = new Dot[dots.length];
        this.dots = dots;

        sortDots();
    }

    public double[] calculateSides()
    {
        double[] sides = new double[dots.length];

        for (int i = 0; i < dots.length - 1; i++)
        {
            sides[i] = dots[i].calculateDistanceTo(dots[i + 1]);
        }
        sides[dots.length - 1] = dots[dots.length - 1].calculateDistanceTo(dots[0]);

        return sides;
    }

    @Override
    public double calculateSquare()
    {
        double s = 0;

        for (int i = 1; i < dots.length - 1; i++)
        {
            double a = dots[0].calculateDistanceTo(dots[i]);
            double b = dots[0].calculateDistanceTo(dots[i + 1]);
            double c = dots[i].calculateDistanceTo(dots[i + 1]);

            double p = (a + b + c) / 2;

            s += Math.sqrt(p * (p - a) * (p - b) * (p - c));
        }

        return s;
    }

    @Override
    public double calculatePerimeter()
    {
        double p = 0;
        double[] sides = calculateSides();

        for (int i = 0; i < dots.length; i++)
        {
            p += sides[i];
        }

        return p;
    }

    public double calculateHeight(Dot dot, Line line)
    {
        return dot.calculateDistanceTo(line);
    }

    private void sortDots(){
        Dot temp;

        //Выбор конкретной начальной точки
        for (int i = 1; i < dots.length; i++)
        {
            if (dots[i].x < dots[0].x || dots[i].x == dots[0].x && dots[i].y < dots[0].y)
            {
                temp = dots[0];
                dots[0] = dots[i];
                dots[i] = temp;
            }
        }

        //Сортировка по окружности
        double slope1;
        for (int i = 1; i < dots.length - 1; i++)
        {
            if (dots[i].x != dots[0].x)
            {
                slope1 = (dots[i].y - dots[0].y) / (dots[i].x - dots[0].x);
            }
            else
            {
                temp = dots[i];
                dots[i] = dots[1];
                dots[1] = temp;
                continue;
            }

            for (int j = i + 1; j < dots.length; j++)
            {
                double slope2;
                if (dots[j].x != dots[0].x)
                {
                    slope2 = (dots[j].y - dots[0].y) / (dots[j].x - dots[0].x);
                }
                else
                {
                    temp = dots[j];
                    dots[j] = dots[1];
                    dots[1] = temp;
                    break;
                }

                if (slope2 > slope1)
                {
                    slope1 = slope2;

                    temp = dots[i];
                    dots[i] = dots[j];
                    dots[j] = temp;
                }
            }
        }
    }
}
