package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.event.NavigationEvent;
import io.github.sachintha_madubashana.libraryos.navigation.ScenesManager;
import io.github.sachintha_madubashana.libraryos.navigation.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

import static io.github.sachintha_madubashana.libraryos.navigation.ScenesManager.*;

public class MainController {
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
    private ScrollPane sidebarScroll;
    @FXML
    private ImageView logoImage;

    private final ScenesManager scenesManager;

    public MainController() {
        this.scenesManager = new ScenesManager();
    }

    @FXML
    private void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("icon/icon.png"))));
        initializeIcons();
        configureSidebarScroll(sidebarScroll);

        contentArea.addEventHandler(NavigationEvent.NAVIGATE, event -> {
            navigate(event.getTargetScene());
            event.consume();
        });

        dashboardBtn.setOnAction(actionEvent -> navigate(DASHBOARD));
        addBookBtn.setOnAction(actionEvent -> navigate(ADD_BOOK));
        addMemberBtn.setOnAction(actionEvent -> navigate(ADD_MEMBER));
        manageMemberBtn.setOnAction(actionEvent -> navigate(MANAGE_MEMBER));
        issueBookBtn.setOnAction(actionEvent -> navigate(ISSUE_BOOK));
        returnBookBtn.setOnAction(actionEvent -> navigate(RETURN_BOOK));
        historyBtn.setOnAction(actionEvent -> navigate(HISTORY));
        signOutBtn.setOnAction(actionEvent -> signOut());

        ImageView userIcon = new ImageView(String.valueOf(Launcher.class.getResource("image/user.png")));
        userIcon.setFitWidth(18);
        userIcon.setFitHeight(18);
        userIcon.setPreserveRatio(true);

        StackPane avatarContainer = new StackPane(userIcon);
        avatarContainer.getStyleClass().add("sidebar-avatar");

        userBtn.setGraphic(avatarContainer);

        navigate(DASHBOARD);
    }

    private void navigate(String sceneName) {
        scenesManager.replaceScene(contentArea, sceneName);
    }

    private void initializeIcons() {
        iconGenerator(dashboardBtn, DASHBOARD);
        iconGenerator(addBookBtn, ADD_BOOK);
        iconGenerator(addMemberBtn, ADD_MEMBER);
        iconGenerator(manageMemberBtn, MANAGE_MEMBER);
        iconGenerator(issueBookBtn, ISSUE_BOOK);
        iconGenerator(returnBookBtn, RETURN_BOOK);
        iconGenerator(historyBtn, HISTORY);
        iconGenerator(signOutBtn, Feather.LOG_OUT);
    }

    private void iconGenerator(Button sceneButton, String sceneName) {
        FontIcon icon = scenesManager.getIcon(sceneName);
        icon.setIconSize(20);
        sceneButton.setGraphic(icon);
    }

    private void iconGenerator(Button sceneButton, Feather featherIcon) {
        FontIcon icon = new FontIcon(featherIcon);
        icon.setIconSize(20);
        sceneButton.setGraphic(icon);
    }

    private void signOut() {
        Stage currentStage = (Stage) signOutBtn.getScene().getWindow();
        currentStage.close();
        new WindowManager().openLoginStage();
    }
    private void configureSidebarScroll(ScrollPane scrollPane) {
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();

            double current = scrollPane.getVvalue();
            double speed = 0.0015;

            double newValue = current - (delta * speed);

            newValue = Math.max(0, Math.min(1, newValue));
            scrollPane.setVvalue(newValue);
            event.consume();
        });
    }
}
