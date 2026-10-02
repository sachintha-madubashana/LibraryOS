package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.PageHeader;
import io.github.sachintha_madubashana.libraryos.model.Route;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class MainController {

    private final HashMap<String, Route> scenes = new HashMap<>();
    @FXML
    private BorderPane contentArea;
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

    private static final String DASHBOARD= "dashboard";
    private static final String ADD_BOOK= "addBook";
    private static final String ADD_MEMBER= "addMember";
    private static final String MANAGE_MEMBER= "manageMember";
    private static final String ISSUE_BOOK= "issueBook";
    private static final String RETURN_BOOK= "returnBook";
    private static final String HISTORY= "borrowingHistory";

    @FXML
    private void initialize() {
        scenes();
        dashboardBtn.setOnAction(actionEvent -> replaceScene(DASHBOARD));
        addBookBtn.setOnAction(actionEvent -> replaceScene(ADD_BOOK));
        addMemberBtn.setOnAction(actionEvent -> replaceScene(ADD_MEMBER));
        manageMemberBtn.setOnAction(actionEvent -> replaceScene(MANAGE_MEMBER));
        issueBookBtn.setOnAction(actionEvent -> replaceScene(ISSUE_BOOK));
        returnBookBtn.setOnAction(actionEvent -> replaceScene(RETURN_BOOK));
        historyBtn.setOnAction(actionEvent -> replaceScene(HISTORY));

        replaceScene(DASHBOARD);
    }

    private void scenes() {
        String date = LocalDate.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH));
        scenes.put(DASHBOARD, new Route(DASHBOARD, "view/dashboard.fxml", "Dashboard", "Welcome back. Today is " + date));
        scenes.put(ADD_BOOK , new Route(ADD_BOOK, "view/addBook.fxml", "Add Book", "Add a new book to the library."));
        scenes.put(ADD_MEMBER, new Route(ADD_MEMBER, "view/addMember.fxml", "Add Member", "Add a new member to the library."));
        scenes.put(MANAGE_MEMBER, new Route(MANAGE_MEMBER, "view/manageMember.fxml", "Manage Member", "Manage existing members in the library."));
        scenes.put(ISSUE_BOOK, new Route(ISSUE_BOOK, "view/issueBook.fxml", "Issue Book", "Issue a book to a member."));
        scenes.put(RETURN_BOOK, new Route(RETURN_BOOK, "view/returnBook.fxml", "Return Book", "Return a book from a member."));
        scenes.put(HISTORY, new Route(HISTORY, "view/borrowingHistory.fxml", "Borrowing History", "View borrowing history of members."));
    }

    private void replaceScene(String scene) {
        if (scenes.containsKey(scene)) {
            Node currentCenter = contentArea.getCenter();
            if (currentCenter != null && scene.equals(currentCenter.getProperties().get("viewName"))) {
                return;
            }

            Route route = scenes.get(scene);
            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(route.getUrl()));
            try {
                Node newNode = fxmlLoader.load();
                newNode.getProperties().put("viewName", route.getViewName());
                contentArea.setCenter(newNode);
                addPageHeader(route.getTitle(), route.getSubtitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addPageHeader(String title, String subtitle) {
        PageHeader pageHeader = new PageHeader("Library Management System".toUpperCase(), title, subtitle);
        pageHeader.setPadding(new javafx.geometry.Insets(24, 24, 0, 24));
        pageHeader.getChildren().add(new Separator(Orientation.HORIZONTAL));
        contentArea.setTop(pageHeader);
    }
}
