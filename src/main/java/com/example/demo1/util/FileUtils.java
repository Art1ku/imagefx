package com.example.demo1.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class FileUtils {

    public static Image loadImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Image File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.bmp"));
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            return new Image(file.toURI().toString());
        }
        return null;
    }

    public static void saveImage(WritableImage img) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = chooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                var pixelBuffer = IntBuffer.allocate((int) img.getWidth() * (int) img.getHeight());
                img.getPixelReader().getPixels(
                        0, 0,
                        (int) img.getWidth(), (int) img.getHeight(),
                        javafx.scene.image.PixelFormat.getIntArgbInstance(),
                        pixelBuffer,
                        (int) img.getWidth()
                );


                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write("// RAW ARGB DATA //".getBytes());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4 * pixelBuffer.capacity());
                    pixelBuffer.rewind();
                    while (pixelBuffer.hasRemaining()) {
                        byteBuffer.putInt(pixelBuffer.get());
                    }
                    fos.write(byteBuffer.array());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
