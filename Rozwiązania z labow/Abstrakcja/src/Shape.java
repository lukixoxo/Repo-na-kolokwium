// --- Plik: Shape.java ---

/**
 * INTERFEJS SHAPE (Kształt)
 * W interfejsie nie ma już pól. Definiujemy jedynie, co każdy kształt MUSI umieć zrobić.
 */
public interface Shape {
    // Zmiana: toSvg przyjmuje teraz parametry (np. style, transformacje), 
    // które doklejamy do wewnątrz znacznika.
    String toSvg(String parameters);
}