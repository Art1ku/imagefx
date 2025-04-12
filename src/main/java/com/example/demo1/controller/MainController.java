package com.example.demo1.controller;

import com.example.demo1.model.ImageProcessor;
import com.example.demo1.util.FileUtils;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class MainController {
    private final ImageView originalView;
    private final ImageView processedView;
    private final ComboBox<String> filters;
    private final Label loadingLabel;

    private Image originalImage;
    private WritableImage processedImage;

    public MainController(ImageView originalView, ImageView processedView, ComboBox<String> filters, Label loadingLabel) {
        this.originalView = originalView;
        this.processedView = processedView;
        this.filters = filters;
        this.loadingLabel = loadingLabel;
    }

    public void loadImage() {
        originalImage = FileUtils.loadImage();
        if (originalImage != null) {
            originalView.setImage(originalImage);
        }
    }

    public void applyFilter() {
        if (originalImage == null) return;

        loadingLabel.setVisible(true);

        Task<Image> task = new Task<>() {
            @Override
            protected Image call() {
                return ImageProcessor.apply(originalImage, filters.getValue());
            }
        };

        task.setOnSucceeded(e -> {
            processedImage = (WritableImage) task.getValue();
            processedView.setImage(processedImage);
            loadingLabel.setVisible(false);
        });

        task.setOnFailed(e -> {
            loadingLabel.setText("Ошибка при обработке!");
            loadingLabel.setVisible(false);
        });

        new Thread(task).start();
    }

    public void saveImage() {
        if (processedImage != null) {
            FileUtils.saveImage(processedImage);
        }
    }
}
