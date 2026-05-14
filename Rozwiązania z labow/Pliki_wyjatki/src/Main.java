// --- Plik: Main.java ---

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String csvPath = "family.csv";
        String binPath = "family.bin";

        System.out.println("--- TEST: ZADANIE 1 - 6 (Wczytywanie z CSV) ---");
        try {
            // Wczytanie osób z pliku wraz z obsługą relacji i zadawaniem pytań w terminalu.
            List<Person> people = Person.fromCsv(csvPath);
            
            System.out.println("\nLista wczytanych i sprawdzonych osób z pliku CSV:");
            for (Person p : people) {
                System.out.println("- " + p.toString());
                for (Person child : p.getChildren()) {
                    System.out.println("   Dziecko: " + child.getFullName());
                }
            }

            System.out.println("\n--- TEST: ZADANIE 7 (Zapis do pliku binarnego) ---");
            Person.toBinaryFile(people, binPath);

            System.out.println("\n--- TEST: ZADANIE 7 (Odczyt z pliku binarnego) ---");
            List<Person> binaryPeople = Person.fromBinaryFile(binPath);
            System.out.println("Wczytano " + binaryPeople.size() + " osób z pliku binarnego.");
            
        } catch (AmbiguousPersonException e) {
            System.err.println("Przerwano działanie programu: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}