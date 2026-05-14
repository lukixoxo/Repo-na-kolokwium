// --- Plik: SvgScene.java ---

import java.io.FileWriter;
import java.io.IOException;

/**
 * KLASA SVG SCENE (Scena rysunku)
 * Przechowuje wielokąty i potrafi zapisać je wszystkie jako jeden obrazek do pliku.
 */
public class SvgScene {

    // ZADANIE 5: Prywatna tablica 3 referencji do obiektów Polygon.
    private Polygon[] polygons = new Polygon[3];
    // Zmienna przechowująca informację, w które miejsce wrzucić następny element.
    private int nextIndex = 0;

    // ZADANIE 5: Dodaje wielokąt. Jeśli miejsce się skończy (nextIndex wyniesie 3), nadpisuje od początku.
    public void addPolygon(Polygon p) {
        // Operacja modulo '%' zabezpiecza przed wyjściem poza rozmiar tablicy (3).
        // Np. gdy nextIndex = 3, 3 % 3 daje resztę 0, więc zaczynamy od nowa!
        polygons[nextIndex % polygons.length] = p;
        nextIndex++;
    }

    // ZADANIE 6: Tworzy jeden wielki tekst sklejając wszystkie reprezentacje toSvg() wielokątów z tablicy.
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        for (Polygon p : polygons) {
            if (p != null) {
                // Doklejamy SVG konkretnego wielokąta + nowa linia dla czytelności
                sb.append(p.toSvg()).append("\n");
            }
        }
        return sb.toString();
    }

    // ZADANIE 8: Zapisuje scenę do fizycznego pliku na dysku.
    public void save(String path) {
        // 1. OBLICZANIE CAŁKOWITEGO ROZMIARU (Bounding Boxa całej sceny)
        double totalMaxX = 0, totalMaxY = 0;
        
        for (Polygon p : polygons) {
            if (p != null) {
                BoundingBox box = p.boundingBox();
                // Sprawdzamy dokąd sięga najdalszy punkt po prawej (x + szerokość) i na dole (y + wysokość)
                double rightEdge = box.x() + box.width();
                double bottomEdge = box.y() + box.height();
                
                if (rightEdge > totalMaxX) totalMaxX = rightEdge;
                if (bottomEdge > totalMaxY) totalMaxY = bottomEdge;
            }
        }

        // 2. TWORZENIE PLIKU
        // Konstrukcja try-catch z 'FileWriter' chroni przed błędem, np. gdy nie mamy uprawnień zapisu na dysku.
        try (FileWriter writer = new FileWriter(path)) {
            // Piszemy główny znacznik <svg>, ustalając rozmiar płótna na nasze wartości krańcowe
            writer.write(String.format("<svg width=\"%f\" height=\"%f\" xmlns=\"http://www.w3.org/2000/svg\">\n", totalMaxX, totalMaxY));
            
            // Wstawiamy wklejone wielokąty
            writer.write(this.toSvg());
            
            // Zamykamy znacznik
            writer.write("</svg>");
            System.out.println("Plik SVG poprawnie zapisany w: " + path);
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }
}