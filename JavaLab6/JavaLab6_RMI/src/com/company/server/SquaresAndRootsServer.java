package com.company.server;

import com.company.common.SquaresAndRoots;

import java.rmi.RemoteException;
import java.util.Arrays;

public class SquaresAndRootsServer implements SquaresAndRoots {

    @Override
    public double[][] squareAndRoot(double[] array) throws RemoteException {

        for (double elem : array){

            System.out.print(elem + " ");

        }

        double[] squares = Arrays.stream(array).map(x -> x * x).toArray();
        double[] roots = Arrays.stream(array).map(Math::sqrt).toArray();

        return new double[][]{squares, roots};
    }
}
