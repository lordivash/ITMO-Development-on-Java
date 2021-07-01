package com.company;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        String serverName = "localhost";
        int port = 12900;

        try (Socket clientSocket = new Socket(serverName, port)) {

            System.out.println("Connection to the " + serverName + ":" + port + " succeeded");

            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))){

                System.out.print("Write your message: ");
                String message = consoleReader.readLine();

                out.write(message + "\n");
                out.flush();

                String respond = in.readLine();
                System.out.println("Server respond: " + respond);

                System.out.println("App is closing");

            }

        }
        catch (IOException e) {

            e.printStackTrace();

        }

    }
}
