package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;

public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button addBookBtn;

    @FXML
    private Button addMemberBtn;

    @FXML
    private Button manageMemberBtn;

    @FXML
    private Button issueBookBtn;

    @FXML
    private Button returnBookBtn;

    @FXML
    private Button historyBtn;

    private final HashMap<String, String> scenes = new HashMap<>();

    @FXML
    private void initialize() {
        scenes();
        dashboardBtn.setOnAction((actionEvent)->{
            replaceScene("dashboard");
        });
        addBookBtn.setOnAction((actionEvent)->{
            replaceScene("addBook");
        });
        addMemberBtn.setOnAction((actionEvent)->{
            replaceScene("addMember");
        });
        manageMemberBtn.setOnAction((actionEvent)->{
            replaceScene("manageMember");
        });
        issueBookBtn.setOnAction((actionEvent)->{
            replaceScene("issueBook");
        });
        returnBookBtn.setOnAction((actionEvent)->{
            replaceScene("returnBook");
        });
        historyBtn.setOnAction((actionEvent)->{
            replaceScene("borrowingHistory");
        });
    }

    private void scenes() {
        scenes.put("dashboard", "view/dashboard.fxml");
        scenes.put("addBook", "view/addBook.fxml");
        scenes.put("addMember", "view/addMember.fxml");
        scenes.put("manageMember", "view/manageMember.fxml");
        scenes.put("issueBook", "view/issueBook.fxml");
        scenes.put("returnBook", "view/returnBook.fxml");
        scenes.put("borrowingHistory", "view/borrowingHistory.fxml");
    }

    private void replaceScene(String scene) {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(scenes.get(scene)));
        try {
            root.setCenter(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
