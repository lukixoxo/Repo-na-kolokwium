// --- Plik: Family.java ---

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KLASA FAMILY (Rodzina)
 * Organizuje osoby wykorzystując Mapy (Map), inaczej "Słowniki".
 */
public class Family {

    // ZADANIE 6 i 8: Mapa przechowująca osoby wg ich imienia i nazwiska.
    // Zadanie 8 każe nam obsłużyć przypadki osób o tych samych danych (np. dwóch "Janów Kowalskich").
    // Dlatego zamiast pojedynczej 'Person', przechowujemy Listę '<List<Person>>' pod danym kluczem!
    private Map<String, List<Person>> members = new HashMap<>();

    // ZADANIE 7 i 8: Metoda add() przyjmująca wariadyczną listę osób.
    // Zapis 'Person... persons' oznacza, że możemy tu włożyć jedną osobę, trzy, 
    // albo od razu całą tablicę (np. add(osoba1, osoba2, osoba3)). Dla Javy wewnątrz to jest tablica.
    public void add(Person... persons) {
        
        // Pętla iterująca po wszystkich przekazanych do dodania osobach
        for (Person p : persons) {
            // Generujemy klucz (Imię + spacja + nazwisko)
            String key = p.getFirstName() + " " + p.getLastName();

            // Używamy metody z Javy 8 'computeIfAbsent'.
            // Jeśli pod tym kluczem w Mapie nie ma jeszcze żadnej listy, to utwórz nową, pustą ArrayListę.
            // Zabezpiecza nas to przed błędem NullPointerException.
            members.computeIfAbsent(key, k -> new ArrayList<>());

            // Pobieramy listę spod danego klucza i po prostu dopisujemy do niej nową osobę.
            members.get(key).add(p);
        }
    }

    // ZADANIE 6 i 8: Zwraca osoby pasujące do klucza (z zachowaniem odpowiedniego sortowania).
    // W poleceniu mowa o zwracaniu "tablicy osób", ale ponieważ Dział 5 to kontenery, 
    // konwencyjnie stosujemy Listę (zwrócenie zwykłej tablicy to dodanie w return '.toArray()').
    public List<Person> get(String fullName) {
        
        // Pobieramy listę osób z mapy. Jeśli kogoś takiego nie ma, zwracamy pustą listę zamiast nulla.
        List<Person> foundPersons = members.get(fullName);
        if (foundPersons == null) {
            return new ArrayList<>();
        }

        // 1. ZADANIE 8: Wymóg posortowania zwróconych osób od najstarszej do najmłodszej.
        // Klasa Person implementuje Comparable, więc Collections.sort zrobi to za nas!
        Collections.sort(foundPersons);

        // 2. Zwracamy posortowaną listę wyników.
        return foundPersons;
    }
}