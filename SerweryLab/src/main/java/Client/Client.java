package Client;

import org.example.ChatBox;
import org.example.ClientReceiver;
import org.example.Main;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client   {
    Socket socket;

    public Client() {
        try {
            socket = new Socket("localhost", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws Exception {
        Client client = new Client();
        OutputStream outputStream = null;
        try {
            outputStream = client.socket.getOutputStream();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pr = new PrintWriter(outputStream, true);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Zaloguj się za pomocą komendy /login [nickname]");
//        String loginmessage = scanner.nextLine();
        String nickname = JOptionPane.showInputDialog("Podaj swój login: ");
        if(nickname == null || nickname.trim().isEmpty()){
            System.exit(0);
        }
        String loginmessage = "/login "+nickname;

        // ZMIANA 1: Najpierw sprawdzam, czy wpisano "/login ", a dopiero potem próbuje wyciągnąć nick.
        // Wcześniej było to przed "if", co powodowało wyrzucenie błędu ArrayIndexOutOfBoundsException,
        // jeśli użytkownik nie wpisał spacji. Zwróć uwagę na spację po słowie "/login ".

        if(loginmessage.startsWith("/login ")) {
            // ZMIANA 2: Przeniesienie wyciągania loginu do wnętrza instrukcji if
            String login = loginmessage.split(" ", 2)[1];

            ChatBox chatBox = new ChatBox(pr);
            ClientReceiver clientReceiver = new ClientReceiver(client.socket, chatBox);
            clientReceiver.start();

            // ZMIANA 3: Przeniosłem wysyłanie loginmessage tutaj. Wysłane zostanie tylko, jeśli to poprawny login.
            pr.println(loginmessage);
        } else {
            // ZMIANA 4: Czytelniejszy komunikat o błędzie logowania
            throw new Exception("Logowanie nieudane. Musisz zacząć od /login [nickname]");
        }

//        while (true){
//            // ZMIANA 5 (KLUCZOWA dla Zadania 4a): Zmiana w wysyłaniu wiadomości.
//            // Usunięto dodawanie "login + ": " przed wiadomością.
//            // Teraz klient wysyła "czysty" tekst z konsoli (np. "/online", "/w ktos wiadomosc" albo "Czesc!").
//            // Dodawaniem prefixu z loginem dla zwykłych wiadomości musi zająć się klasa ClientThread po stronie serwera.
//            String messageToSend = scanner.nextLine();
//            pr.println(messageToSend);
//        }

    }
}