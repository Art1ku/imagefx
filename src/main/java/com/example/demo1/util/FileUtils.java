package com.example.demo1.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.PixelWriter;

public class FileUtils {


    public static BufferedImage loadImageFromFile() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.bmp"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveImageToFile(BufferedImage image) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = chooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                ImageIO.write(image, "PNG", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Image convertToFXImage(BufferedImage bufferedImage) {
        WritableImage fxImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        PixelWriter pixelWriter = fxImage.getPixelWriter();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int argb = bufferedImage.getRGB(x, y);
                Color color = new Color(argb, true);
                pixelWriter.setColor(x, y, javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 255.0));
            }
        }

        return fxImage;
    }

    public static BufferedImage convertToBufferedImage(Image fxImage) {
        BufferedImage bufferedImage = new BufferedImage((int) fxImage.getWidth(), (int) fxImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        javafx.scene.image.PixelReader pixelReader = fxImage.getPixelReader();
        for (int x = 0; x < fxImage.getWidth(); x++) {
            for (int y = 0; y < fxImage.getHeight(); y++) {
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                int argb = (int) (color.getOpacity() * 255) << 24 | (int) (color.getRed() * 255) << 16 | (int) (color.getGreen() * 255) << 8 | (int) (color.getBlue() * 255);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        return bufferedImage;
    }
}
