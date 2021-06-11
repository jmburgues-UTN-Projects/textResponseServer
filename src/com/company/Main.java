package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        int portNumber = (args.length != 1) ? 3000 : Integer.parseInt(args[0]);

        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                System.out.println("Listening for connections...");

                Socket clientSocket = serverSocket.accept();
                System.out.println(LocalDateTime.now() + " Incoming connection from: " + clientSocket.getInetAddress());

                DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());

                String inputLine, outputLine;

                // Initiate conversation with client
                Response response = new Response();
                // Calls for Reponse and prints output to client.
                outputLine = response.processInput(null);

                String msg ="";
                Boolean continuar = true;

                while(!serverSocket.isClosed() && continuar) {
                    dataOut.writeUTF(outputLine);
                    dataOut.flush();
                    if(!outputLine.equals("Bye.")){
                        msg = dataIn.readUTF();
                        outputLine = response.processInput(msg);
                    }else{
                        continuar = false;
                    }
                }
                System.out.println(LocalDateTime.now() + "Disconnected: " + clientSocket.getInetAddress());
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}