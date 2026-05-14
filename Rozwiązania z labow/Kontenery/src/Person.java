// --- Plik: Person.java ---

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * KLASA PERSON (Osoba)
 * ZADANIE 4: Implementujemy interfejs Comparable<Person>. 
 * Dzięki temu Java dowie się, jak ma automatycznie "porównywać" i sortować osoby.
 */
public class Person implements Comparable<Person> {
    
    // ZADANIE 1: Prywatne pola imienia, nazwiska oraz daty urodzenia.
    // Używamy LocalDate z najnowszych bibliotek Javy (java.time) do obsługi dat.
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    // ZADANIE 2: Zbiór (Set) przechowujący dzieci.
    // Zbiór w Javie charakteryzuje się tym, że nie ma w nim duplikatów (ta sama osoba nie może być dodana 2 razy).
    // Inicjalizujemy go jako HashSet - najpopularniejszą i bardzo szybką implementację zbioru.
    private Set<Person> children = new HashSet<>();

    // Konstruktor do wygodnego tworzenia osób
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // Gettery dla podstawowych pól
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }

    // ZADANIE 2: Metoda adopt() przypisująca dziecko.
    // Metoda Set.add() domyślnie zwraca boolean: 'true' jeśli dodano nowy element, 
    // lub 'false', jeśli ten element już był w zbiorze. Od razu zwracamy ten wynik!
    public boolean adopt(Person child) {
        return this.children.add(child);
    }

    // ZADANIE 4: Metoda compareTo() wymuszona przez interfejs Comparable.
    // Definiujemy zasady: jak sprawdzić, która osoba "jest mniejsza/wcześniejsza".
    @Override
    public int compareTo(Person other) {
        // Osoba wcześniejsza to ta urodzona wcześniej. 
        // LocalDate ma już wbudowaną metodę compareTo, więc po prostu jej używamy!
        return this.birthDate.compareTo(other.birthDate);
    }

    // ZADANIE 3 i 4: Zwraca najmłodsze dziecko ze zbioru.
    public Person getYoungestChild() {
        if (children.isEmpty()) {
            return null; // Zwracamy null, jeśli osoba nie ma dzieci
        }
        
        // ZADANIE 4: Ponieważ zaimplementowaliśmy Comparable (sortowanie rosnąco od najstarszego), 
        // najmłodsze dziecko to to, które ma "największą" (najpóźniejszą) datę urodzenia.
        // Możemy użyć Collections.max(), które samo przejdzie przez zbiór i znajdzie to maksimum.
        return Collections.max(children);
    }

    // ZADANIE 5: Metoda zwracająca dzieci w formie POSORTOWANEJ Listy (List).
    public List<Person> getChildren() {
        // 1. Tworzymy nową listę (ArrayList) na bazie naszego zbioru (Set).
        // Zbiory nie mają kolejności, ale listy już tak.
        List<Person> sortedChildren = new ArrayList<>(this.children);
        
        // 2. Sortujemy listę. Ponieważ klasa Person implementuje Comparable, 
        // metoda sort() domyślnie ułoży osoby od najstarszej do najmłodszej.
        Collections.sort(sortedChildren);
        
        // 3. Zwracamy gotową listę.
        return sortedChildren;
    }

    // Metoda pomocnicza ułatwiająca wyświetlanie osoby na ekranie.
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + birthDate + ")";
    }
}