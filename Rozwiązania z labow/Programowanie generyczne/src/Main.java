import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("--- Zadanie 1-3: CustomList ---");
        CustomList<String> list = new CustomList<>();
        list.add("Kot");         // add() działa jak addLast()
        list.addFirst("Pies");   // [Pies, Kot]
        list.addLast("Chomik");  // [Pies, Kot, Chomik]
        
        System.out.println("Rozmiar: " + list.size());
        System.out.println("Pierwszy element: " + list.getFirst());
        System.out.println("Element na indeksie 1: " + list.get(1));
        
        System.out.println("Usunięto z końca: " + list.removeLast());
        System.out.println("Wydruk z pomocą stream():");
        list.stream().forEach(e -> System.out.println(" - " + e));


        System.out.println("\n--- Zadanie 4: Class i Inheritance ---");
        List<Object> mixedList = Arrays.asList(1, "Napis", 2.5, "Kolejny napis", 5);
        // Filtrujemy tylko Stringi:
        List<Object> stringsOnly = GenericUtils.filterByInheritance(mixedList, String.class);
        System.out.println("Zostawiono tylko napisy: " + stringsOnly);

        // Filtrowanie z uwzględnieniem dziedziczenia (Integer i Double dziedziczą po Number):
        List<Object> numbersOnly = GenericUtils.filterByInheritance(mixedList, Number.class);
        System.out.println("Zostawiono typy numeryczne: " + numbersOnly);


        System.out.println("\n--- Zadanie 5: Przedziały (Interval) ---");
        List<Integer> intList = Arrays.asList(1, 5, 8, 10, 15, 20);
        // Szukamy w przedziale otwartym (5, 15), czyli szukamy elementów > 5 i < 15. Powinny pasować 8 i 10.
        long count = GenericUtils.countElementsInInterval(intList, 5, 15);
        System.out.println("Elementów w przedziale (5, 15) jest: " + count);


        System.out.println("\n--- Zadanie 6: Komparator Numeryczny ---");
        List<Integer> listA = Arrays.asList(10, 10);      // suma 20
        List<Double> listB = Arrays.asList(5.5, 5.0, 5.0); // suma 15.5
        
        // Komparator pozwala na zestawienie ich ze sobą!
        int result = GenericUtils.bySumComparator().compare(listA, listB);
        if (result > 0) {
            System.out.println("Lista A ma większą sumę niż Lista B");
        } else if (result < 0) {
            System.out.println("Lista B ma większą sumę niż Lista A");
        } else {
            System.out.println("Sumy są równe");
        }
    }
}