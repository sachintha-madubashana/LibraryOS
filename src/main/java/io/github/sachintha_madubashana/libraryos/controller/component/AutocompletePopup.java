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

import java.util.Collection;
import java.util.function.Consumer;

public class AutocompletePopup<T> extends Popup {

    private static final double CELL_HEIGHT = 38;
    private final VBox container;
    private final ListView<T> listView;
    private final ObservableList<T> suggestions;
    private StringConverter<T> converter;
    private Consumer<T> onSuggestionSelected;
    private int maxVisibleRows = 6;

    public AutocompletePopup() {
        suggestions = FXCollections.observableArrayList();

        container = new VBox();
        container.getStyleClass().addAll(
                "autocomplete-popup"
        );

        listView = new ListView<>();
        listView.getStyleClass().add("autocomplete-list");

        listView.setItems(suggestions);

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
        suggestions.add(item);
        updatePopupHeight();
    }

    public void clearSuggestions() {
        suggestions.clear();
        hide();
    }

    public ObservableList<T> getSuggestions() {
        return suggestions;
    }

    // SUGGESTIONS
    public void setSuggestions(Collection<T> items) {
        suggestions.setAll(items);
        listView.getSelectionModel().clearSelection();
        updatePopupHeight();
    }

    // DISPLAY
    public void showBelow(Node anchor) {
        if (suggestions.isEmpty()) {
            hide();
            return;
        }

        Bounds bounds = anchor.localToScreen(
                anchor.getBoundsInLocal()
        );

        if (bounds == null) {
            return;
        }

        double width = bounds.getWidth();

        container.setPrefWidth(width);
        container.setMinWidth(width);
        container.setMaxWidth(width);

        updatePopupHeight();

        if (!isShowing()) {
            show(anchor, bounds.getMinX(), bounds.getMaxY() + 4);
        } else {
            setX(bounds.getMinX());
            setY(bounds.getMaxY() + 4);
        }
    }

    private void updatePopupHeight() {
        int visibleRows = Math.min(suggestions.size(), maxVisibleRows);

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