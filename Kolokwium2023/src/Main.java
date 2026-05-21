import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    System.out.println("=== START TESTÓW KROKI 1-11 ===\n");

    // 1. Tworzymy ląd - kwadrat od (0,0) do (20,20)
    List<Point> landPoints = new ArrayList<>();
    landPoints.add(new Point(0, 0));
    landPoints.add(new Point(20, 0));
    landPoints.add(new Point(20, 20));
    landPoints.add(new Point(0, 20));
    Land wyspa = new Land(landPoints);

    // 2. Tworzymy miasta
    // Śródlądowe: środek w (10,10), ściana 2. Całe na lądzie.
    City inlandCity = new City(new Point(10, 10), "Grodzisk (Śródlądowe)", 2);

    // Portowe: środek w (19,10), ściana 4. Prawe wierzchołki murów będą wychodzić poza ląd (na x=21).
    City portCity = new City(new Point(19, 10), "Gdynia (Portowe)", 4);

    // Wodne (błędne): środek w (30,30). Całkowicie poza lądem.
    City waterCity = new City(new Point(30, 30), "Atlantyda", 2);

    System.out.println("--- TEST DODAWANIA MIAST I RZUCANIA WYJĄTKU (Kroki 7-8) ---");
    try {
      wyspa.addCity(inlandCity);
      inlandCity.portCheck(wyspa); // Pamiętaj o wywołaniu tego tak jak ustaliliśmy!
      System.out.println("[OK] Dodano: " + inlandCity.getName());

      wyspa.addCity(portCity);
      portCity.portCheck(wyspa);
      System.out.println("[OK] Dodano: " + portCity.getName());

      System.out.print("[TEST WYJĄTKU] Próba dodania miasta na wodzie... ");
      wyspa.addCity(waterCity);
      System.out.println("[BŁĄD] Wyjątek nie został rzucony!");
    } catch (RuntimeException e) {
      System.out.println("Złapano oczekiwany wyjątek z wiadomością: " + e.getMessage());
    }

    // Pokażemy, że portCheck zadziałało:
    // W City mamy pole prywatne port, więc nie mamy do niego tu bezpośredniego dostępu,
    // ale zobaczymy efekty tego pola za chwilę przy dodawaniu ryb!

    System.out.println("\n--- TEST ZASOBÓW (Kroki 10-11) ---");
    // Tworzymy surowce na mapie
    List<Resource> surowce = new ArrayList<>();
    // Surowce dla Grodziska (środek 10,10):
    surowce.add(new Resource(new Point(10, 11), Resource.Type.Wood)); // Dystans 1 (w zasięgu)
    surowce.add(new Resource(new Point(10, 12), Resource.Type.Fish)); // Dystans 2 (w zasięgu, ale Grodzisk to nie port!)

    // Surowce dla Gdyni (środek 19,10):
    surowce.add(new Resource(new Point(20, 10), Resource.Type.Coal)); // Dystans 1 (w zasięgu)
    surowce.add(new Resource(new Point(21, 10), Resource.Type.Fish)); // Dystans 2 (w zasięgu, ryby w porcie!)

    // Surowiec daleko od wszystkich
    surowce.add(new Resource(new Point(50, 50), Resource.Type.Wood));

    double zasieg = 2.5;

    inlandCity.addResourcesInRange(surowce, zasieg);
    portCity.addResourcesInRange(surowce, zasieg);

    // Dzięki dostępowi pakietowemu możemy bezpośrednio wejść do zmiennej .resources
    System.out.println("Zasoby zebrane przez " + inlandCity.getName() + " (oczekiwane tylko Wood):");
    System.out.println(inlandCity.resources);

    System.out.println("Zasoby zebrane przez " + portCity.getName() + " (oczekiwane Coal, Fish):");
    System.out.println(portCity.resources);

    System.out.println("\n=== KONIEC TESTÓW ===");
  }
}