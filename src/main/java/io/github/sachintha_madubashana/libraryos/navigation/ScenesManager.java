package io.github.sachintha_madubashana.libraryos.navigation;

import io.github.sachintha_madubashana.libraryos.Launcher;
import io.github.sachintha_madubashana.libraryos.controller.component.PageHeader;
import io.github.sachintha_madubashana.libraryos.model.Route;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class ScenesManager {
    public static final String DASHBOARD = "dashboard";
    public static final String ADD_BOOK = "addBook";
    public static final String ADD_MEMBER = "addMember";
    public static final String MANAGE_MEMBER = "manageMember";
    public static final String ISSUE_BOOK = "issueBook";
    public static final String RETURN_BOOK = "returnBook";
    public static final String HISTORY = "borrowingHistory";
    private final HashMap<String, Route> scenes = new HashMap<>();

    public ScenesManager() {
        String date = LocalDate.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH));
        scenes.put(DASHBOARD, new Route(DASHBOARD, "view/dashboard.fxml", "Dashboard", "Welcome back. Today is " + date, new FontIcon(Feather.GRID)));
        scenes.put(ADD_BOOK, new Route(ADD_BOOK, "view/addBook.fxml", "Add Book", "Add a new book to the library.", new FontIcon(Feather.BOOK)));
        scenes.put(ADD_MEMBER, new Route(ADD_MEMBER, "view/addMember.fxml", "Add Member", "Add a new member to the library.", new FontIcon(Feather.USER_PLUS)));
        scenes.put(MANAGE_MEMBER, new Route(MANAGE_MEMBER, "view/manageMember.fxml", "Manage Member", "Manage existing members in the library.", new FontIcon(Feather.USERS)));
        scenes.put(ISSUE_BOOK, new Route(ISSUE_BOOK, "view/issueBook.fxml", "Issue Book", "Issue a book to a member.", new FontIcon(Feather.ARROW_RIGHT)));
        scenes.put(RETURN_BOOK, new Route(RETURN_BOOK, "view/returnBook.fxml", "Return Book", "Return a book from a member.", new FontIcon(Feather.ARROW_LEFT)));
        scenes.put(HISTORY, new Route(HISTORY, "view/borrowingHistory.fxml", "Borrowing History", "View borrowing history of members.", new FontIcon(Feather.LIST)));
    }


    public void replaceScene(BorderPane contentArea, String scene) {
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
                addPageHeader(contentArea, route.getTitle(), route.getSubtitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addPageHeader(BorderPane contentArea, String title, String subtitle) {
        PageHeader pageHeader = new PageHeader("Library Management System".toUpperCase(), title, subtitle);
        pageHeader.setPadding(new javafx.geometry.Insets(24, 24, 0, 24));
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(0, 0, 8, 0));
        pageHeader.getChildren().add(separator);
        contentArea.setTop(pageHeader);
    }

    public FontIcon getIcon(String viewName) {
        return scenes.get(viewName).getIcon();
    }
}
