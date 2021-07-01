package com.company;

import java.util.Comparator;

public class FigureComporator implements Comparator<Figure>
{
    @Override
    public int compare(Figure figure1, Figure figure2)
    {
        if (figure1.calculateSquare() > figure2.calculateSquare())
        {
            return 1;
        }
        else if (figure1.calculateSquare() == figure2.calculateSquare())
        {
            return 0;
        }
        else return -1;
    }
}
