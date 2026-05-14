// --- Plik: Main.java ---

/**
 * KLASA MAIN (Główna)
 * To jest serce Twojego programu. Tu znajduje się metoda 'main',
 * od której komputer zawsze zaczyna czytać i uruchamiać Twój kod.
 */
public class Main {
    
    // ZADANIE 0: Klasa Main z metodą statyczną main.
    public static void main(String[] args) {
        
        System.out.println("--- ZADANIE 1 ---");
        // Tworzymy z naszej "foremki" nowy, rzeczywisty Punkt.
        Point pointA = new Point();
        
        // Ustawiamy mu pola (ponieważ są public, możemy to zrobić prosto w ten sposób).
        pointA.x = 2.0;
        pointA.y = 3.5;
        
        // Wyświetlamy punkt. Automatycznie uruchomi się tutaj nasza metoda toString()!
        System.out.println("Mój punkt to: " + pointA);

        System.out.println("\n--- ZADANIE 2 ---");
        // Sprawdzamy wygenerowany kod SVG
        System.out.println("Kod SVG punktu: " + pointA.toSvg());

        System.out.println("\n--- ZADANIE 3 ---");
        // Przesuwamy obecny punkt pointA o wartości (1.0, 1.0)
        pointA.translate(1.0, 1.0);
        System.out.println("Punkt po przesunięciu translate(1,1): " + pointA);

        // Tworzymy NOWY punkt przesunięty o (5.0, 5.0) od obecnego pointA
        Point pointB = pointA.translated(5.0, 5.0);
        System.out.println("Nowy wygenerowany punkt pointB: " + pointB);
        System.out.println("Stary punkt pointA się NIE zmienił: " + pointA);

        System.out.println("\n--- ZADANIE 4 ---");
        // Tworzymy nowy odcinek
        Segment mySegment = new Segment();
        // Przypinamy mu nasze dwa wygenerowane wcześniej punkty jako jego końce
        mySegment.p1 = pointA;
        mySegment.p2 = pointB;
        
        System.out.println("Długość mojego odcinka: " + mySegment.length());

        System.out.println("\n--- ZADANIE 5 ---");
        // Tworzymy tablicę trzech odcinków. 
        // Nawiasy [] oznaczają, że to nie jest jeden Segment, tylko ich kolekcja (tablica).
        Segment[] array = new Segment[3];
        
        // Wkładamy nasz stary odcinek na pozycję 0
        array[0] = mySegment; 
        
        // Tworzymy na szybko dwa nowe odcinki bezpośrednio w tablicy (na pozycjach 1 i 2)
        array[1] = new Segment();
        array[1].p1 = new Point(); // domyślnie x=0.0, y=0.0
        array[1].p2 = new Point(); 
        array[1].p2.x = 100.0;     // robimy go bardzo długim, żeby na pewno wygrał
        
        array[2] = new Segment();
        array[2].p1 = pointA;
        array[2].p2 = pointA;      // Odcinek łączący ten sam punkt (długość wyniesie 0)

        // Używamy metody statycznej (dlatego wywołujemy ją przez nazwę klasy Segment.longestSegment)
        Segment winner = Segment.longestSegment(array);
        System.out.println("Najdłuższy odcinek z tablicy ma długość: " + winner.length());
    }
}