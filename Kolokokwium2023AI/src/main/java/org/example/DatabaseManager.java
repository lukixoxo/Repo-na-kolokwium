package org.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseManager {
    // Ścieżka relatywna - plik bazy zostanie utworzony w katalogu "images"
    private static final String DB_URL = "jdbc:sqlite:images/index.db";

    public static void initializeDatabase() {
        File dir = new File("images");
        if (!dir.exists()) dir.mkdir();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Utworzenie tabeli zgodnie z wymaganiami. Zastosowano AUTOINCREMENT dla ID.
            String sql = "CREATE TABLE IF NOT EXISTS blur_records (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "path TEXT," +
                    "size INTEGER," +
                    "delay INTEGER" +
                    ")";
            stmt.execute(sql);
            System.out.println("Połączono z bazą danych i zweryfikowano tabelę.");
        } catch (Exception e) {
            System.err.println("Błąd bazy danych: " + e.getMessage());
        }
    }

    public static void saveRecord(String path, int size, long delay) {
        // Zastosowanie PreparedStatement chroni przed błędami i dba o prawidłowe typowanie
        String sql = "INSERT INTO blur_records (path, size, delay) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, path);
            pstmt.setInt(2, size);
            pstmt.setLong(3, delay);

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Błąd podczas zapisu rekordu: " + e.getMessage());
        }
    }
}