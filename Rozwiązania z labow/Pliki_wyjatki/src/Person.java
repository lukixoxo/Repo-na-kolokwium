// --- Plik: Person.java ---

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * KLASA PERSON (Osoba)
 * Implementuje Serializable (Zadanie 7), aby można było ją zapisywać do plików binarnych.
 */
public class Person implements Serializable, Comparable<Person> {
    
    // Ustalony SerialVersionUID przydaje się przy klasach implementujących Serializable
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    
    // ZADANIE 1: Uwzględnienie daty śmierci
    private LocalDate deathDate;
    
    private List<Person> children = new ArrayList<>();

    public Person(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDate getDeathDate() { return deathDate; }
    public String getFullName() { return firstName + " " + lastName; }
    
    public void addChild(Person child) {
        this.children.add(child);
    }

    public List<Person> getChildren() {
        return children;
    }

    @Override
    public int compareTo(Person other) {
        return this.birthDate.compareTo(other.birthDate);
    }

    @Override
    public String toString() {
        String deathStr = (deathDate != null) ? deathDate.toString() : "żyje";
        return getFullName() + " (" + birthDate + " - " + deathStr + ")";
    }

    // --- ZADANIE 1: Metoda wytwórcza fromCsvLine ---
    public static Person fromCsvLine(String line) throws NegativeLifespanException {
        // Parametr limit=-1 sprawia, że zachowujemy również puste wartości na końcu linii
        String[] parts = line.split(",", -1); 
        
        // Rozdzielanie "Imię i nazwisko"
        String[] nameParts = parts[0].trim().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = (nameParts.length > 1) ? nameParts[1] : "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(parts[1].trim(), formatter);
        LocalDate deathDate = null;

        // Jeśli 3. kolumna (indeks 2) nie jest pusta, parsujemy datę śmierci
        if (parts.length > 2 && !parts[2].trim().isEmpty()) {
            deathDate = LocalDate.parse(parts[2].trim(), formatter);
            
            // ZADANIE 3: Rzucanie wyjątku jeśli długość życia jest ujemna
            if (deathDate.isBefore(birthDate)) {
                throw new NegativeLifespanException(
                    String.format("Błąd logiki: %s ma datę śmierci (%s) wcześniejszą niż data urodzin (%s).", 
                                  parts[0], deathDate, birthDate)
                );
            }
        }

        // Metoda tworzy samą Osobę, pomijamy tu referencje do rodziców.
        return new Person(firstName, lastName, birthDate, deathDate);
    }

    // --- ZADANIE 2, 4, 5, 6: Metoda fromCsv ---
    public static List<Person> fromCsv(String path) throws IOException, AmbiguousPersonException {
        List<Person> allPersons = new ArrayList<>();
        Map<String, Person> byName = new HashMap<>();
        
        // Pomocnicza mapa przechowująca nieprzetworzone nazwy rodziców dla każdego dziecka
        Map<Person, List<String>> temporaryParentRecords = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(path));
        boolean isFirstLine = true;

        // FAZA 1: Budowanie obiektów
        for (String line : lines) {
            if (isFirstLine) { isFirstLine = false; continue; } // Pomijamy nagłówek
            if (line.trim().isEmpty()) continue;

            try {
                Person p = Person.fromCsvLine(line);
                String fullName = p.getFullName().trim();

                // ZADANIE 4: Wykrywanie duplikatów osób
                if (byName.containsKey(fullName)) {
                    throw new AmbiguousPersonException("Błąd: Plik zawiera duplikat osoby: '" + fullName + "'. Nazwy muszą być unikalne.");
                }

                byName.put(fullName, p);
                allPersons.add(p);

                // Zbieramy wpisy o rodzicach (kolumny indeks 3 i 4)
                String[] parts = line.split(",", -1);
                List<String> parentNames = new ArrayList<>();
                if (parts.length > 3 && !parts[3].trim().isEmpty()) parentNames.add(parts[3].trim());
                if (parts.length > 4 && !parts[4].trim().isEmpty()) parentNames.add(parts[4].trim());
                
                temporaryParentRecords.put(p, parentNames);

            } catch (NegativeLifespanException | DateTimeParseException e) {
                System.err.println("Pominięto błędny wiersz z powodu błędu dat: " + e.getMessage());
            }
        }

        // FAZA 2: Łączenie w referencje (ZADANIE 5 i ZADANIE 6)
        Scanner scanner = new Scanner(System.in);
        for (Person child : allPersons) {
            List<String> parentNames = temporaryParentRecords.get(child);
            for (String pName : parentNames) {
                Person parent = byName.get(pName);
                if (parent != null) {
                    try {
                        // Walidujemy możliwość bycia rodzicem (Zadanie 6)
                        parent.validateParentingConstraint(child);
                        
                        // Jeśli się udało – dodajemy
                        parent.addChild(child);
                    } catch (ParentingAgeException e) {
                        // Przechwytujemy wyjątek i wymuszamy decyzję użytkownika
                        System.out.println("\nOSTRZEŻENIE: " + e.getMessage());
                        System.out.print("Czy mimo to na pewno chcesz dodać to dziecko do rodzica? Wpisz 'Y' aby potwierdzić, dowolny inny znak aby odrzucić: ");
                        String answer = scanner.nextLine().trim();
                        if ("Y".equalsIgnoreCase(answer)) {
                            parent.addChild(child);
                            System.out.println("-> Dodano relację (Wymuszone przez użytkownika).");
                        } else {
                            System.out.println("-> Odrzucono relację.");
                        }
                    }
                }
            }
        }
        
        return allPersons;
    }

    // Pomocnicza walidacja do ZADANIA 6
    private void validateParentingConstraint(Person child) throws ParentingAgeException {
        long ageAtBirth = ChronoUnit.YEARS.between(this.birthDate, child.getBirthDate());
        
        if (ageAtBirth < 15) {
            throw new ParentingAgeException(
                String.format("Rodzic %s w chwili urodzenia dziecka %s miał(a) mniej niż 15 lat (dokładnie %d lat).", 
                this.getFullName(), child.getFullName(), ageAtBirth));
        }
        
        if (this.deathDate != null && this.deathDate.isBefore(child.getBirthDate())) {
            throw new ParentingAgeException(
                String.format("Rodzic %s nie żył(a) w momencie narodzin dziecka %s.", 
                this.getFullName(), child.getFullName()));
        }
    }

    // --- ZADANIE 7: Zapis i odczyt binarny ---
    public static void toBinaryFile(List<Person> list, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Pomyślnie zapisano listę osób do pliku binarnego: " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> fromBinaryFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Person>) ois.readObject();
        }
    }
}