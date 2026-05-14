import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * ZADANIE 1 i 2: KLASA CUSTOM LIST
 * Implementacja jednokierunkowej listy wiązanej.
 * Dziedziczy po AbstractList, co wymusza nadpisanie m.in. get() i size().
 */
public class CustomList<T> extends AbstractList<T> {

    // Wewnętrzna klasa reprezentująca pojedynczy węzeł listy
    private class Node {
        T value;
        Node next;

        Node(T value) {
            this.value = value;
        }
    }

    // Wskaźniki na początek (head) i koniec (tail) listy
    private Node head;
    private Node tail;
    private int size = 0;

    // --- ZADANIE 1: Metody specyficzne dla listy ze wskaźnikami ---

    public void addLast(T value) {
        Node newNode = new Node(value);
        if (tail == null) {
            // Jeśli lista jest pusta, nowy węzeł jest jednocześnie początkiem i końcem
            head = tail = newNode;
        } else {
            // W przeciwnym razie podpinamy go na koniec i przesuwamy wskaźnik tail
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T getLast() {
        if (tail == null) throw new NoSuchElementException("Lista jest pusta");
        return tail.value;
    }

    public void addFirst(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public T getFirst() {
        if (head == null) throw new NoSuchElementException("Lista jest pusta");
        return head.value;
    }

    public T removeFirst() {
        if (head == null) throw new NoSuchElementException("Lista jest pusta");
        T val = head.value;
        head = head.next; // Przesuwamy początek na kolejny element
        
        // Jeśli usunęliśmy jedyny element, lista jest całkowicie pusta (tail też musi być null)
        if (head == null) tail = null; 
        
        size--;
        return val;
    }

    public T removeLast() {
        if (head == null) throw new NoSuchElementException("Lista jest pusta");
        
        // Przypadek 1: Tylko jeden element na liście
        if (head == tail) {
            T val = head.value;
            head = tail = null;
            size--;
            return val;
        }
        
        // Przypadek 2: Więcej elementów. Ponieważ to lista jednokierunkowa, 
        // musimy przejść od początku, żeby znaleźć węzeł tuż przed 'tail'
        Node curr = head;
        while (curr.next != tail) {
            curr = curr.next;
        }
        
        T val = tail.value;
        tail = curr;       // Przedostatni element staje się nowym ogonem
        tail.next = null;  // Odcinamy stary ogon
        size--;
        return val;
    }

    // --- ZADANIE 2: Nadpisanie metod z AbstractList ---

    @Override
    public boolean add(T t) {
        addLast(t); // Działa dokładnie tak samo jak addLast
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indeks poza zakresem: " + index);
        }
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    // --- ZADANIE 3: Iterator i Strumienie ---

    @Override
    public Iterator<T> iterator() {
        // Implementujemy anonimową klasę Iteratora
        return new Iterator<T>() {
            private Node curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T val = curr.value;
                curr = curr.next;
                return val;
            }
        };
    }

    @Override
    public Stream<T> stream() {
        // Konwertujemy nasz własny iterator na nowoczesny strumień (Stream)
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size, 0), false);
    }
}