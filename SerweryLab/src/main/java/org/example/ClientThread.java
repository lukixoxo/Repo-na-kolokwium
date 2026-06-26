package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    Server server;
    String userName;

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String message;
            while ((message = br.readLine()) != null) {
                if (message.startsWith("/login ")) {
                    // ZADANIE 3a: Logowanie i powitanie
                    // Wyciągamy login
                    userName = message.split(" ", 2)[1];
                    // 1. Wysyłamy wiadomość tekstową do okien czatu
                    server.broadcast("[BROADCAST] " + userName + " dołączył do czatu.");
                    // 2. Informujemy wszystkich starych klientów, żeby dodali nowego do swoich list
                    server.broadcast("[ONLINE] " + userName);
                    // 3. Wysyłamy nowemu klientowi (temu, który właśnie się zalogował) wszystkich obecnych na serwerze
                    for (ClientThread cl : server.clients) {
                        if (!cl.userName.equals(this.userName) && cl.userName != null) {
                            // Wysyłamy tylko do tego JEDNEGO klienta (używamy this.send)
                            this.send("[ONLINE] " + cl.userName);
                        }
                    }

                } else if (message.equals("/online")) {
                    // ZADANIE 3b: Zwracanie aktywnych (poprawiona pętla)
                    send("[BROADCAST] Lista uzytkownikow:");
                    for (ClientThread cl : server.clients) {
                        if (cl.userName != null) {
                            send("[BROADCAST] - " + cl.userName);
                        }
                    }

                } else if (message.startsWith("/w ")) {
                    // ZADANIE 4b: Wiadomości prywatne
                    String[] parts = message.split(" ", 3);
                    if (parts.length == 3) {
                        String recipient = parts[1];
                        String privateMessage = parts[2];
                        boolean found = false;

                        for (ClientThread cl : server.clients) {
                            if (recipient.equals(cl.userName)) {
                                cl.send("[BROADCAST] [Wiadomość prywatna od " + this.userName + "]: " + privateMessage);
                                this.send("[BROADCAST] [Do " + recipient + "]: " + privateMessage);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            this.send("[BROADCAST] Użytkownik '" + recipient + "' nie jest zalogowany.");
                        }
                    } else {
                        this.send("[BROADCAST] Złe użycie komendy. Użyj: /w [odbiorca] [wiadomość]");
                    }

                } else {
                    // ZADANIE 4a: Zwykłe wiadomości z prefixem dodawanym przez SERWER
                    if (userName != null) {
                        server.broadcast("[BROADCAST] " + userName + ": " + message);
                    }
                }
            }

        } catch (IOException e) {
                throw new RuntimeException(e);
        }finally {
            // TUTAJ JEST POPRAWNE MIEJSCE NA SPRZĄTANIE
            server.clients.remove(this);
            if (userName != null) {
                server.broadcast("[BROADCAST] " + userName + " opuścił czat.");
                server.broadcast("[OFFLINE] " + userName);
            }
        }
    }

    public void send(String message){
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
