package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {

    public static void applyBoxBlur(String inputPath, String outputPath, int kernelSize) throws Exception {
        BufferedImage image = ImageIO.read(new File(inputPath));
        int width = image.getWidth();
        int height = image.getHeight();

        // Nowy obraz o takich samych wymiarach i typie
        BufferedImage result = new BufferedImage(width, height, image.getType());

        // Odczytanie liczby rdzeni do ustalenia puli wątków
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        // Podział obrazu w pionie (każdy wątek otrzymuje swój "pas" pikseli do obliczeń)
        int chunkHeight = height / cores;

        for (int i = 0; i < cores; i++) {
            final int startY = i * chunkHeight;
            // Ostatni wątek dociąga do samego końca obrazu (wyrównanie niedokładności dzielenia)
            final int endY = (i == cores - 1) ? height : (i + 1) * chunkHeight;

            threads[i] = new Thread(() -> {
                processChunk(image, result, width, height, startY, endY, kernelSize);
            });
            threads[i].start(); // Uruchomienie wątku
        }

        // Czekamy na zakończenie pracy wszystkich uruchomionych wątków
        for (Thread t : threads) {
            t.join();
        }

        // Zapis gotowego pliku na dysk
        ImageIO.write(result, "png", new File(outputPath));
    }

    // Metoda wykonująca rzeczywiste obliczenia dla wyznaczonego fragmentu
    private static void processChunk(BufferedImage src, BufferedImage dst, int width, int height, int startY, int endY, int kernelSize) {
        int radius = kernelSize / 2; // Odległość od środkowego piksela

        for (int y = startY; y < endY; y++) {
            for (int x = 0; x < width; x++) {
                int r = 0, g = 0, b = 0;
                int count = 0; // Licznik faktycznie uwzględnionych sąsiadów

                // Przeszukiwanie sąsiedztwa [x-radius, x+radius] oraz [y-radius, y+radius]
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        // Weryfikacja, czy sąsiad nie wychodzi poza granice obrazu
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            int rgb = src.getRGB(nx, ny);

                            // Ekstrakcja poszczególnych kanałów i dodanie do sumy (niezależne operacje RGB)
                            r += (rgb >> 16) & 0xFF;
                            g += (rgb >> 8) & 0xFF;
                            b += rgb & 0xFF;
                            count++;
                        }
                    }
                }

                // Średnia arytmetyczna
                r /= count;
                g /= count;
                b /= count;

                // Odczytanie pierwotnej wartości alpha (przezroczystości), aby uniknąć jej zniekształcenia
                int alpha = (src.getRGB(x, y) >> 24) & 0xFF;

                // Złożenie kolorów z powrotem do jednej wartości int
                int newPixel = (alpha << 24) | (r << 16) | (g << 8) | b;
                dst.setRGB(x, y, newPixel);
            }
        }
    }
}