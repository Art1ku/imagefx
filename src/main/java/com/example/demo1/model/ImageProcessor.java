package com.example.demo1.model;

import javafx.scene.image.*;

public class ImageProcessor {

    public static WritableImage apply(Image img, String filter) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();

        PixelReader reader = img.getPixelReader();
        WritableImage output = new WritableImage(w, h);
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                var c = reader.getColor(x, y);

                switch (filter) {
                    case "Grayscale" -> {
                        double gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3.0;
                        writer.setColor(x, y, javafx.scene.paint.Color.color(gray, gray, gray, c.getOpacity()));
                    }
                    case "Invert" -> {
                        writer.setColor(x, y,
                                javafx.scene.paint.Color.color(1 - c.getRed(), 1 - c.getGreen(), 1 - c.getBlue(), c.getOpacity()));
                    }
                    case "Blur" -> {
                        writer.setColor(x, y, averageColor(reader, x, y, w, h));
                    }
                    default -> writer.setColor(x, y, c);
                }
            }
        }

        return output;
    }


    private static javafx.scene.paint.Color averageColor(PixelReader reader, int x, int y, int width, int height) {
        int count = 0;
        double r = 0, g = 0, b = 0, a = 0;

        for (int dy = -10; dy <= 10; dy++) {
            for (int dx = -10; dx <= 10; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    var c = reader.getColor(nx, ny);
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                    a += c.getOpacity();
                    count++;
                }
            }
        }

        return javafx.scene.paint.Color.color(r / count, g / count, b / count, a / count);
    }
}
