package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        String serverName = "localhost";
        int port = 12900;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server: " + serverName + "\nOpened on port: " + port);

            try (Socket clientSocket = serverSocket.accept()){

                System.out.println("Client has connected");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))){

                    String message = in.readLine();
                    System.out.println("Received message from client: " + message);

                    out.write(message);
                    out.flush();
                    System.out.println("Message redirected to the client");

                    System.out.println("Server closing");

                }

            }

        }


//        final Socket clientSocket = serverSocket.accept();
//        Runnable runnable = () -> handleRequest(clientSocket);
//        new Thread(runnable).start();

    }

    private static void handleRequest(Socket clientSocket){

        System.out.println("In request");

    }
}
