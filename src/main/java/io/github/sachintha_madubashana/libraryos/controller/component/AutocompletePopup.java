package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.StringConverter;

import java.util.List;
import java.util.function.Consumer;

public class AutocompletePopup<T> extends Popup {

    private static final double CELL_HEIGHT = 38;
    private final VBox container;
    private final ListView<T> listView;
    private StringConverter<T> converter;
    private Consumer<T> onSuggestionSelected;
    private int maxVisibleRows = 6;

    public AutocompletePopup() {
        container = new VBox();
        container.getStyleClass().addAll("autocomplete-popup");

        listView = new ListView<>();
        listView.getStyleClass().add("autocomplete-list");

        listView.setFixedCellSize(CELL_HEIGHT);
        listView.setFocusTraversable(false);

        // Configure popup container
        container.getChildren().add(listView);
        container.setPadding(new Insets(4));

        // Configure popup
        getContent().add(container);

        setAutoFix(true);
        setAutoHide(true);
        setHideOnEscape(true);

        // Configure suggestion cells
        configureCells();

        // Handle suggestion selection
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                T selected = listView.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    selectSuggestion(selected);
                }
            }
        });

        updatePopupHeight();
    }

    // CELL CONFIGURATION
    private void configureCells() {
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(converter != null
                            ? converter.toString(item)
                            : item.toString());
                }
            }
        });
    }

    public void addSuggestion(T item) {
        listView.getItems().add(item);
        updatePopupHeight();
    }

    public void clearSuggestions() {
        listView.getItems().clear();
        hide();
    }

    public ObservableList<T> getSuggestions() {
        return listView.getItems();
    }

    // SUGGESTIONS
    public void setSuggestions(ObservableList<T> items) {
        listView.setItems(items);
        listView.getSelectionModel().clearSelection();
        updatePopupHeight();
    }

    public void setSuggestions(List<T> items) {
        listView.setItems(FXCollections.observableArrayList(items));
        listView.getSelectionModel().clearSelection();
        updatePopupHeight();
    }

    // DISPLAY
    public void showBelow(Node anchor) {
        if (listView.getItems().isEmpty()) {
            hide();
            return;
        }

        Bounds bounds = anchor.localToScreen(anchor.getLayoutBounds());

        if (bounds == null) {
            return;
        }

        double width = bounds.getWidth();

        container.setPrefWidth(width);
        container.setMinWidth(width);
        container.setMaxWidth(width);

        updatePopupHeight();

        container.applyCss();
        double shadowOffsetX = container.getLayoutBounds().getMinX() - container.getBoundsInLocal().getMinX();
        double targetX = bounds.getMinX() - shadowOffsetX;
        double targetY = bounds.getMaxY();

        if (!isShowing()) {
            show(anchor, targetX, targetY);
        } else {
            setX(targetX);
            setY(targetY);
        }
    }

    private void updatePopupHeight() {
        int visibleRows = Math.min(listView.getItems().size(), maxVisibleRows);

        double height = visibleRows * CELL_HEIGHT;

        listView.setPrefHeight(height);
        listView.setMinHeight(height);
        listView.setMaxHeight(height);
    }

    // SELECTION
    private void selectSuggestion(T suggestion) {
        if (onSuggestionSelected != null) {
            onSuggestionSelected.accept(suggestion);
        }

        hide();
    }

    public void setOnSuggestionSelected(Consumer<T> callback) {
        this.onSuggestionSelected = callback;
    }

    // CONFIGURATION
    public void setConverter(StringConverter<T> converter) {
        this.converter = converter;
        listView.refresh();
    }

    public void setMaxVisibleRows(int maxVisibleRows) {
        if (maxVisibleRows < 1) {
            throw new IllegalArgumentException("Maximum visible rows must be at least 1.");
        }

        this.maxVisibleRows = maxVisibleRows;
        updatePopupHeight();
    }

    public ListView<T> getListView() {
        return listView;
    }
}