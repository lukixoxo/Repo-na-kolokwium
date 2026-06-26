package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    // Volatile gwarantuje, że zmiany z wątku GUI będą widoczne dla wątku sieciowego
    private static volatile int kernelSize = 3;
    private static final int PORT = 5000;

    public static void main(String[] args) {
        // Inicjalizacja GUI w wątku Event Dispatch Thread (standard w Swing)
        SwingUtilities.invokeLater(Server::setupUI);

        // Inicjalizacja bazy danych (tworzy plik i tabelę, jeśli jej brak)
        DatabaseManager.initializeDatabase();

        // Uruchomienie gniazda sieciowego i nasłuchiwanie
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer nasłuchuje na porcie " + PORT);

            // Nieskończona pętla - serwer po obsłużeniu klienta wraca do nasłuchiwania
            while (true) {
                // accept() blokuje działanie pętli do momentu podłączenia klienta
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Podłączono klienta!");
                    handleClient(clientSocket);
                } catch (Exception e) {
                    System.err.println("Błąd podczas obsługi klienta: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Konfiguracja interfejsu użytkownika z suwakiem
    private static void setupUI() {
        JFrame frame = new JFrame("Panel Serwera - Box Blur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 150);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Promień filtra (rozmiar jądra): " + kernelSize);
        JSlider slider = new JSlider(1, 15, kernelSize);

        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // Nasłuchiwacz zmian - pilnuje wymogu nieparzystości
        slider.addChangeListener(e -> {
            int value = slider.getValue();
            if (value % 2 == 0) {
                // Skok do najbliższej wartości nieparzystej
                value = (value == 15) ? 13 : value + 1;
                slider.setValue(value);
            }
            kernelSize = value;
            label.setText("Promień filtra (rozmiar jądra): " + kernelSize);
        });

        frame.add(label);
        frame.add(slider);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Główna logika przetwarzania pojedynczego klienta
    private static void handleClient(Socket socket) throws Exception {
        // KROK 1 & 2: Weryfikacja folderu i przygotowanie ścieżek
        File imagesDir = new File("images");
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String inputFileName = "images/" + timestamp + "_input.png";
        String outputFileName = "images/" + timestamp + "_output.png";

        // KROK 3: Odbieranie pliku od klienta
        DataInputStream input = new DataInputStream(socket.getInputStream());
        long fileSize = input.readLong(); // Pobranie długości pliku zgodnie z logiką Client.java

        try (FileOutputStream fos = new FileOutputStream(inputFileName)) {
            byte[] buffer = new byte[8192];
            int count;
            long receivedSize = 0;

            // Pętla czyta dane aż do osiągnięcia zadeklarowanego rozmiaru pliku
            while (receivedSize < fileSize) {
                // Bezpieczne ustalenie wielkości paczki bajtów do pobrania
                int bytesToRead = (int) Math.min((long) buffer.length, fileSize - receivedSize);
                count = input.read(buffer, 0, bytesToRead);
                if (count == -1) break;
                fos.write(buffer, 0, count);
                receivedSize += count;
            }
        }
        System.out.println("Odebrano plik: " + inputFileName);

        // KROK 4: Przekształcenie obrazu (Box Blur) z pomiarem czasu
        long startTime = System.currentTimeMillis();
        ImageProcessor.applyBoxBlur(inputFileName, outputFileName, kernelSize);
        long delay = System.currentTimeMillis() - startTime;
        System.out.println("Przetworzono obraz w " + delay + " ms z jądrem " + kernelSize);

        // KROK 5: Zapis parametrów operacji do bazy danych
        DatabaseManager.saveRecord(outputFileName, kernelSize, delay);

        // KROK 6: Odesłanie wynikowego pliku
        File outputFile = new File(outputFileName);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // Analogicznie do odbierania - najpierw informujemy klienta o rozmiarze pliku
        output.writeLong(outputFile.length());
        try (FileInputStream fis = new FileInputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = fis.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            output.flush();
        }
        System.out.println("Odesłano gotowy obraz.\n");
    }
}