package com.company;


import java.io.FileWriter;
import java.io.IOException;

abstract class Figure implements Comparable<Figure>{
    Dot[] dots;

    abstract public double calculateSquare();
    abstract public double calculatePerimeter();

    @Override
    public int compareTo(Figure figure)
    {
        if (this.calculateSquare() > figure.calculateSquare())
        {
            return 1;
        }
        else if (this.calculateSquare() == figure.calculateSquare())
        {
            return 0;
        }
        else return -1;
    }

    public void toFile()
    {
        try(FileWriter writer = new FileWriter("figure.txt", false))
        {
            StringBuilder text = new StringBuilder("");
            for (Dot dot : dots) {
                text.append(dot.toString()).append("\n");
            }
            writer.write(text.toString());
            writer.flush();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
