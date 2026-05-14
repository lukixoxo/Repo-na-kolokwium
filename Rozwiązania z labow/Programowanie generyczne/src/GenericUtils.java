import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Klasa grupująca metody pomocnicze do Zadań 4, 5 i 6.
 */
public class GenericUtils {

    // --- ZADANIE 4: Filtrowanie listy na podstawie klasy (i dziedziczenia) ---
    /**
     * Zwraca listę obiektów, które są instancją podanej klasy (lub po niej dziedziczą).
     * Wykorzystanie metody clazz.isInstance(e) natywnie sprawdza dziedziczenie 
     * w sposób bezpieczny (zastępuje ona starsze clazz.isAssignableFrom(e.getClass())).
     */
    public static <T> List<T> filterByInheritance(List<T> list, Class<?> clazz) {
        return list.stream()
                .filter(e -> e != null && clazz.isInstance(e))
                .collect(Collectors.toList());
    }

    // --- ZADANIE 5: Predykat dla otwartego przedziału ---
    /**
     * Zwraca predykat testujący czy wartość (musi implementować Comparable) jest wewnątrz (lower, upper).
     */
    public static <T extends Comparable<T>> Predicate<T> inOpenInterval(T lower, T upper) {
        // compareTo zwraca >0 gdy wartość jest większa, a <0 gdy mniejsza
        return t -> t.compareTo(lower) > 0 && t.compareTo(upper) < 0;
    }

    /**
     * Zlicza elementy w podanym przedziale.
     */
    public static <T extends Comparable<T>> long countElementsInInterval(List<T> list, T lower, T upper) {
        return list.stream()
                .filter(inOpenInterval(lower, upper))
                .count();
    }


    // --- ZADANIE 6: Komparatory kolekcji ---
    /**
     * Komparator nr 1 - Zwykły (Zadanie 6a)
     * Porównuje dwie dowolne kolekcje pod względem liczby ich elementów (size).
     */
    public static Comparator<Collection<?>> bySizeComparator() {
        return Comparator.comparingInt(Collection::size);
    }

    /**
     * Komparator nr 2 - Zmodyfikowany pod liczby i dziedziczenie (Zadanie 6b)
     * Przyjmuje tylko kolekcje przechowujące typy numeryczne (Integer, Double itp.).
     * Porównuje te kolekcje na podstawie łącznej sumy ich elementów.
     */
    public static Comparator<Collection<? extends Number>> bySumComparator() {
        return (c1, c2) -> {
            // Obliczamy sumy mapując wartości na double (uniwersalne dla wszystkich Number)
            double sum1 = c1.stream().mapToDouble(Number::doubleValue).sum();
            double sum2 = c2.stream().mapToDouble(Number::doubleValue).sum();
            
            // Zwracamy wynik porównania sum
            return Double.compare(sum1, sum2);
        };
    }
}