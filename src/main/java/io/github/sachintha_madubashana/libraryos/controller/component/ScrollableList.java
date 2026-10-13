package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Function;

public class ScrollableList<T> extends VBox {

    private final ListView<T> listView;
    private final ObservableList<T> items;
    private HBox header;

    public ScrollableList(List<? extends T> items, Function<T, Node> itemFactory) {
        getStyleClass().setAll("scrollable-list");
        this.items = FXCollections.observableArrayList();
        this.items.addAll(items);

        listView = new ListView<>(this.items);
        listView.getStyleClass().setAll("scrollable-list-view");

        listView.setCellFactory(cellListView -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Node row = itemFactory.apply(item);
                    setGraphic(row);
                    setText(null);
                }
            }
        });

        VBox.setVgrow(listView, Priority.ALWAYS);
        getChildren().add(listView);
    }

    public ScrollableList(Function<T, Node> itemFactory) {
        this(List.of(), itemFactory);
    }

    public void setHeaderText(String text) {
        setHeaderText(text, null);
    }

    public void setHeaderText(String text, Button headerButton) {
        if (header != null) {
            getChildren().remove(header);
        }

        header = createHeader(text, headerButton);
        getChildren().add(0, header);
    }

    public void removeHeader() {
        if (header != null) {
            getChildren().remove(header);
            header = null;
        }
    }

    private HBox createHeader(String text, Button headerButton) {
        Label title = new Label(text);
        title.getStyleClass().setAll("scrollable-list-header-title");

        HBox headerHBox = new HBox();
        headerHBox.getStyleClass().setAll("scrollable-list-header");
        headerHBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerHBox.getChildren().addAll(title, spacer);

        if (headerButton != null) {
            headerButton.getStyleClass().setAll("scrollable-list-header-button");
            headerHBox.getChildren().add(headerButton);
        }

        return headerHBox;
    }

    public void setItems(List<? extends T> newItems) {
        items.setAll(newItems);
    }

    public void addItems(List<? extends T> newItems) {
        items.addAll(newItems);
    }

    public void addItem(T item) {
        items.add(item);
    }

    public boolean removeItem(T item) {
        return items.remove(item);
    }

    public void clearItems() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }

    public ListView<T> getListView() {
        return listView;
    }

    public ObservableList<T> getItems() {
        return items;
    }
}