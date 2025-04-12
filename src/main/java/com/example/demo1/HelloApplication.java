package com.example.demo1;

import com.example.demo1.controller.MainController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ImageView originalView = new ImageView();
        ImageView processedView = new ImageView();
        originalView.setFitWidth(400);
        originalView.setPreserveRatio(true);
        processedView.setFitWidth(400);
        processedView.setPreserveRatio(true);

        ComboBox<String> filterBox = new ComboBox<>();
        filterBox.getItems().addAll("Grayscale", "Invert", "Blur");
        filterBox.setValue("Grayscale");

        Button openBtn = new Button("Open");
        Button applyBtn = new Button("ACTIVATE MACHINE");
        Button saveBtn = new Button("Save");

        Label loadingLabel = new Label("Loading...");
        loadingLabel.setVisible(false);

        HBox topBar = new HBox(10, openBtn, applyBtn, saveBtn, filterBox);
        topBar.setPadding(new Insets(10));

        HBox imagePane = new HBox(10, originalView, processedView);
        imagePane.setPadding(new Insets(10));

        VBox root = new VBox(10, topBar, loadingLabel, imagePane);
        root.setPadding(new Insets(20));

        MainController controller = new MainController(originalView, processedView, filterBox, loadingLabel);
        openBtn.setOnAction(e -> controller.loadImage());
        applyBtn.setOnAction(e -> controller.applyFilter());
        saveBtn.setOnAction(e -> controller.saveImage());

        stage.setTitle("Imager 3000");
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
