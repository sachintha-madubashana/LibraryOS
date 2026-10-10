package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScrollableList extends VBox {

    private final VBox itemList;
    private final List<Node> items;

    public ScrollableList(List<Node> items) {
        itemList = new VBox();
        this.items = items;

        //TODO: Add css for root
        getStyleClass().add("recent-activity");

        ScrollPane scrollPane = new ScrollPane(itemList);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        //TODO: Add css for item list Container
        itemList.getStyleClass().add("activity-list");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        getChildren().add(scrollPane);

        addItems();
    }

    private void addItems(){
        items.forEach(item -> itemList.getChildren().add(item));
    }

    public void setHeaderText(String text){
        setHeaderText(text,null);
    }

    public void setHeaderText(String text, Button headerBtn){
        getChildren().add(0, createHeader(text, headerBtn));
    }

    private HBox createHeader(String text, Button headerBtn){
        Label title = new Label(text);
        title.getStyleClass().add("book-header-title");

        HBox header = new HBox(title);

        if(headerBtn != null) {
            Button addBtn = createHeaderBtn(headerBtn);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            header = new HBox(title, spacer, addBtn);
        }

        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("book-header");

        return header;
    }

    private Button createHeaderBtn(Button headerBtn){
        headerBtn.getStyleClass().add("view-all-button");
        return headerBtn;
    }
}
