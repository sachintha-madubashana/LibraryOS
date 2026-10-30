package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.notification.ForegroundOverlay;
import io.github.sachintha_madubashana.libraryos.controller.component.notification.NotificationOverlay;
import io.github.sachintha_madubashana.libraryos.event.NavigationEvent;
import io.github.sachintha_madubashana.libraryos.event.NotificationEvent;
import io.github.sachintha_madubashana.libraryos.model.Notification;
import io.github.sachintha_madubashana.libraryos.navigation.ScenesManager;
import io.github.sachintha_madubashana.libraryos.navigation.WindowManager;
import io.github.sachintha_madubashana.libraryos.service.NotificationManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

import static io.github.sachintha_madubashana.libraryos.navigation.ScenesManager.*;

public class MainController {
    private final ScenesManager scenesManager;
    private NotificationManager notificationManager;

    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane foregroundPane;
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
    @FXML
    private VBox foregroundNotificationContainer;

    public MainController() {
        this.scenesManager = new ScenesManager();
//        this.notificationManager = new NotificationManager();
    }

    @FXML
    private void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("icon/icon.png"))));
        initializeIcons();
        configureSidebarScroll(sidebarScroll);

        rootPane.addEventHandler(NavigationEvent.NAVIGATE, event -> {
            navigate(event.getTargetScene());
            event.consume();
        });

        rootPane.addEventHandler(NotificationEvent.NOTIFICATION, event -> {
            showAlert(event.getTargetNotification());
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
        userBtn.setOnAction(actionEvent -> rootPane.fireEvent(new NotificationEvent(new Notification("Hi", "This is test notification", Notification.Type.ALERT_SUCCESS))));

        ImageView userIcon = new ImageView(String.valueOf(Launcher.class.getResource("image/user.png")));
        userIcon.setFitWidth(18);
        userIcon.setFitHeight(18);
        userIcon.setPreserveRatio(true);

        StackPane avatarContainer = new StackPane(userIcon);
        avatarContainer.getStyleClass().add("sidebar-avatar");

        userBtn.setGraphic(avatarContainer);

        navigate(DASHBOARD);

        ForegroundOverlay overlay = new NotificationOverlay();
        StackPane.setAlignment(overlay.getContainer(), Pos.BOTTOM_RIGHT);
        StackPane.setMargin(overlay.getContainer(), new Insets(20));
        foregroundPane.getChildren().add(overlay.getContainer());
        notificationManager = new NotificationManager(overlay);
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

    private void showAlert(Notification notification) {
        if(notification.getType().name().startsWith("ALERT")){
            notificationManager.showNotification(notification);
            System.out.println("Show notification");
        } else {
            System.out.println("Show Dialog");
        }
    }
}
