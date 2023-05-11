/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.frontOffice.liveChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private ServerSocket serverSocket;
    private List<ChatHandler> clients;

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(8000);
        clients = new ArrayList<>();
    }

    public void start() {
        System.out.println("Serveur Démarer");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection entrante : " + socket.getRemoteSocketAddress());
                ChatHandler handler = new ChatHandler(socket, this);
                handler.start();
                clients.add(handler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcast(String message) {
        System.out.println(message);
        for (ChatHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.start();
    }
}

class ChatHandler extends Thread {
    private Socket socket;
    private ChatServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ChatHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            username = in.readLine();
            server.broadcast(username + " a rejoint la discution");

            String message;
            while ((message = in.readLine()) != null) {
                server.broadcast(username + ": " + message);
            }

            server.broadcast(username + " a quitter la discution");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
