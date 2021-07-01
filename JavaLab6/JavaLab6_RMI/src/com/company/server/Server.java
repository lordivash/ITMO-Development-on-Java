package com.company.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static String BINDING_NAME = "server.squareAndRoot";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {

        final SquaresAndRootsServer server = new SquaresAndRootsServer();

        final Registry registry = LocateRegistry.createRegistry(1099);

        Remote stub = UnicastRemoteObject.exportObject(server, 0);
        registry.bind(BINDING_NAME, stub);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
