package com.company.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SquaresAndRoots extends Remote {

    double[][] squareAndRoot(double[] array) throws RemoteException;

}
