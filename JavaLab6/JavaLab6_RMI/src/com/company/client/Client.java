package com.company.client;

import com.company.common.SquaresAndRoots;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static String BINDING_NAME = "server.squareAndRoot";

    public static void main(String[] args) throws RemoteException, NotBoundException {

        final Registry registry = LocateRegistry.getRegistry("localhost", 1099);

        SquaresAndRoots squaresAndRoots = (SquaresAndRoots) registry.lookup(BINDING_NAME);

        double[] values = {1, 2, 3, 4, 5, 6, 7};

        double[][] result = squaresAndRoots.squareAndRoot(values);

        for (double[] row : result){

            for (double element : row){

                System.out.print(element + " ");

            }

            System.out.println();

        }
    }

}
