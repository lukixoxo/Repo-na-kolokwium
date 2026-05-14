// --- Plik: Main.java ---

import java.time.LocalDate;
import java.util.List;

/**
 * KLASA MAIN (Główna)
 * Program testujący mechanizmy z Kontenerów (Zadanie 1-8).
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("--- ZADANIE 1: Tworzenie osób i daty ---");
        // Daty tworzymy za pomocą gotowej metody of(ROK, MIESIĄC, DZIEŃ).
        Person p1 = new Person("Jan", "Kowalski", LocalDate.of(1970, 5, 20));
        Person child1 = new Person("Adam", "Kowalski", LocalDate.of(1995, 8, 10));
        Person child2 = new Person("Ewa", "Kowalska", LocalDate.of(2000, 1, 15));
        Person child3 = new Person("Michał", "Kowalski", LocalDate.of(1998, 11, 5));

        // ZADANIE 1: Tworzymy Listę (List) obiektów Person w main
        // List.of to wygodny sposób na szybkie stworzenie listy startowej w nowej Javie.
        List<Person> society = List.of(p1, child1, child2, child3);
        System.out.println("Osoby w programie stworzone bez błędów.");

        
        System.out.println("\n--- ZADANIE 2, 3, 4, 5: Adopcja, najmłodsze dziecko i sortowanie ---");
        // Jan adoptuje dzieci
        p1.adopt(child1);
        p1.adopt(child2);
        p1.adopt(child3);
        
        // Test próby ponownego dodania tego samego dziecka do ZBIORU (Set)
        boolean result = p1.adopt(child1); 
        System.out.println("Czy dodano ponownie Adama? " + result + " (Oczekiwano: false, bo Set nie pozwala na duplikaty)");

        // Kto jest najmłodszy? Powinna to być Ewa (rok 2000)
        System.out.println("Najmłodsze dziecko Jana to: " + p1.getYoungestChild());

        // Wypiszmy wszystkie dzieci posortowane (Zadanie 5)
        System.out.println("Dzieci Jana (od najstarszego do najmłodszego): ");
        for (Person c : p1.getChildren()) {
            System.out.println(" - " + c);
        }

        
        System.out.println("\n--- ZADANIE 6, 7, 8: Rodzina (Family) i Mapy ---");
        Family myFamily = new Family();

        // Stwórzmy kilku "Janów Kowalskich" w różnym wieku, aby przetestować ZADANIE 8 (taka sama nazwa)
        Person duplicate1 = new Person("Jan", "Kowalski", LocalDate.of(2010, 1, 1));
        Person duplicate2 = new Person("Jan", "Kowalski", LocalDate.of(1950, 6, 30));

        // ZADANIE 7: Wariadyczne dodawanie. Podajemy po przecinku dowolną liczbę osób do dodania.
        myFamily.add(p1, child1, duplicate1, duplicate2);

        // Szukamy w Mapie pod kluczem "Jan Kowalski".
        // Zgodnie z Zadaniem 8 powinna zwrócić się posortowana lista wszystkich 3 Janów (1950, 1970, 2010).
        List<String> searchKey = List.of("Jan Kowalski");
        System.out.println("Osoby zmapowane jako 'Jan Kowalski' w rodzinie:");
        List<Person> results = myFamily.get("Jan Kowalski");
        
        for (Person p : results) {
            System.out.println(" - Znaleziono: " + p);
        }
    }
}