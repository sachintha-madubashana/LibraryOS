package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.PageHeader;
import io.github.sachintha_madubashana.libraryos.model.Route;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class MainController {

    private static final String DASHBOARD = "dashboard";
    private static final String ADD_BOOK = "addBook";
    private static final String ADD_MEMBER = "addMember";
    private static final String MANAGE_MEMBER = "manageMember";
    private static final String ISSUE_BOOK = "issueBook";
    private static final String RETURN_BOOK = "returnBook";
    private static final String HISTORY = "borrowingHistory";
    private final HashMap<String, Route> scenes = new HashMap<>();
    private final HashMap<String, Feather> iconsMap = new HashMap<>();
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
    @FXML
    private Button signOutBtn;
    @FXML
    private Button userBtn;

    @FXML
    private void initialize() {
        scenes();
        initializeIconsMap();

        dashboardBtn.setOnAction(actionEvent -> replaceScene(DASHBOARD));
        addBookBtn.setOnAction(actionEvent -> replaceScene(ADD_BOOK));
        addMemberBtn.setOnAction(actionEvent -> replaceScene(ADD_MEMBER));
        manageMemberBtn.setOnAction(actionEvent -> replaceScene(MANAGE_MEMBER));
        issueBookBtn.setOnAction(actionEvent -> replaceScene(ISSUE_BOOK));
        returnBookBtn.setOnAction(actionEvent -> replaceScene(RETURN_BOOK));
        historyBtn.setOnAction(actionEvent -> replaceScene(HISTORY));
        signOutBtn.setOnAction(actionEvent -> signOut());

        iconGenerator(dashboardBtn, DASHBOARD);
        iconGenerator(addBookBtn, ADD_BOOK);
        iconGenerator(addMemberBtn, ADD_MEMBER);
        iconGenerator(manageMemberBtn, MANAGE_MEMBER);
        iconGenerator(issueBookBtn, ISSUE_BOOK);
        iconGenerator(returnBookBtn, RETURN_BOOK);
        iconGenerator(historyBtn, HISTORY);
        iconGenerator(signOutBtn, "signOut");

        ImageView userIcon = new ImageView(String.valueOf(Launcher.class.getResource("image/user.png")));
        userIcon.setFitWidth(18);
        userIcon.setFitHeight(18);
        userIcon.setPreserveRatio(true);

        StackPane avatarContainer = new StackPane(userIcon);
        avatarContainer.getStyleClass().add("sidebar-avatar");

        userBtn.setGraphic(avatarContainer);

        replaceScene(DASHBOARD);
    }

    private void scenes() {
        String date = LocalDate.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH));
        scenes.put(DASHBOARD, new Route(DASHBOARD, "view/dashboard.fxml", "Dashboard", "Welcome back. Today is " + date));
        scenes.put(ADD_BOOK, new Route(ADD_BOOK, "view/addBook.fxml", "Add Book", "Add a new book to the library."));
        scenes.put(ADD_MEMBER, new Route(ADD_MEMBER, "view/addMember.fxml", "Add Member", "Add a new member to the library."));
        scenes.put(MANAGE_MEMBER, new Route(MANAGE_MEMBER, "view/manageMember.fxml", "Manage Member", "Manage existing members in the library."));
        scenes.put(ISSUE_BOOK, new Route(ISSUE_BOOK, "view/issueBook.fxml", "Issue Book", "Issue a book to a member."));
        scenes.put(RETURN_BOOK, new Route(RETURN_BOOK, "view/returnBook.fxml", "Return Book", "Return a book from a member."));
        scenes.put(HISTORY, new Route(HISTORY, "view/borrowingHistory.fxml", "Borrowing History", "View borrowing history of members."));
    }

    private void initializeIconsMap() {
        iconsMap.put(DASHBOARD, Feather.GRID);
        iconsMap.put(ADD_BOOK, Feather.BOOK);
        iconsMap.put(ADD_MEMBER, Feather.USER_PLUS);
        iconsMap.put(MANAGE_MEMBER, Feather.USERS);
        iconsMap.put(ISSUE_BOOK, Feather.ARROW_RIGHT);
        iconsMap.put(RETURN_BOOK, Feather.ARROW_LEFT);
        iconsMap.put(HISTORY, Feather.LIST);
        iconsMap.put("signOut", Feather.LOG_OUT);
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

    private void iconGenerator(Button sceneButton, String sceneName) {
        FontIcon icon = new FontIcon(iconsMap.get(sceneName));
        icon.setIconSize(20);
        sceneButton.setGraphic(icon);
    }

    private void signOut() {
        try {
            Stage currentStage = (Stage) signOutBtn.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("view/login-view.fxml"));
            Stage loginStage = new Stage();
            Scene loginScene = new Scene(loader.load(), 1069, 600);
            loginScene.setFill(Color.TRANSPARENT);
            loginStage.setScene(loginScene);
            loginStage.initStyle(StageStyle.TRANSPARENT);

            currentStage.close();
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
