package io.github.sachintha_madubashana.libraryos;

import io.github.sachintha_madubashana.libraryos.navigation.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class LibraryOS extends javafx.application.Application {
    private static final String FONT_PATH = "/io/github/sachintha_madubashana/libraryos/fonts/";

    @Override
    public void start(Stage stage) throws IOException {
        loadFonts();
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource("css/main.css")).toExternalForm());
        new WindowManager().openLoginStage(stage);
    }

    public void loadFonts() {
        load("inter-regular.ttf");
        load("inter-medium.ttf");
        load("inter-semibold.ttf");
        load("inter-bold.ttf");
        load("isometra-regular.ttf");
    }

    private void load(String fileName) {
        String path = FONT_PATH + fileName;

        try (var stream = LibraryOS.class.getResourceAsStream(path)) {

            if (stream == null) {
                throw new IllegalStateException("Font resource not found: " + path);
            }

            Font font = Font.loadFont(stream, 14);

            if (font == null) {
                throw new IllegalStateException("JavaFX could not parse the font: " + path);
            }

//            System.out.println("Loaded font: " + font.getFamily() + " | " + font.getName());

        } catch (IOException e) {
            throw new IllegalStateException("Error reading font: " + path, e);
        }
    }
}
