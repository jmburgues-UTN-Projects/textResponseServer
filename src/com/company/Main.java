package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int portNumber = (args.length != 1) ? 3000 : Integer.parseInt(args[0]);

        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                System.out.println("Listening for connections...");

                Socket clientSocket = serverSocket.accept();
                System.out.println(LocalDateTime.now() + " Incoming connection from: " + clientSocket.getInetAddress());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;

                // Initiate conversation with client
                Response response = new Response();

                // Calls for Reponse and prints output to client.
                outputLine = response.processInput(null);
    /*
                if(outputLine != null) {
                    for (int i = 0; i < outputLine.length(); i++) {
                        char toPrint = outputLine.charAt(i);
                        out.print(toPrint); // not working
                        TimeUnit.MILLISECONDS.sleep(50);
                    }
                }else{
                    out.println(outputLine);
                }
                */
                out.println(outputLine);

                while ((inputLine = in.readLine()) != null) {

                    outputLine = response.processInput(inputLine);
                    out.println(outputLine);
                    if (outputLine.equals("Bye."))
                        break;
                }
                System.out.println("Closing connection with: " + clientSocket.getInetAddress());
                clientSocket.close();
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
