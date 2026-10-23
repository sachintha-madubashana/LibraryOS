package io.github.sachintha_madubashana.libraryos.controller.component.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class DataTable<T> extends VBox {

    private final TableView<T> tableView;
    private final Label emptyLabel;

    public DataTable() {
        this.tableView = new TableView<>();
        this.emptyLabel = new Label("No data available");

        initialize();
    }

    // --------------------------------------------------------------------------
    // Initialization
    // --------------------------------------------------------------------------

    private void initialize() {
        getStyleClass().add("data-table");
        tableView.getStyleClass().add("data-table-view");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        tableView.setPlaceholder(emptyLabel);
        emptyLabel.getStyleClass().add("data-table-empty");
        setFillWidth(true);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        getChildren().add(tableView);
    }

    // --------------------------------------------------------------------------
    // Columns
    // --------------------------------------------------------------------------

    public <V> void addColumn(DataColumn<T, V> configuration) {
        TableColumn<T, V> column = new TableColumn<>(configuration.getTitle());

        column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(configuration.getValueProvider().apply(cellData.getValue())));

        column.setPrefWidth(configuration.getWidth());
        column.setMinWidth(configuration.getMinWidth());
        column.setMaxWidth(configuration.getMaxWidth());

        column.setSortable(configuration.isSortable());
        column.setResizable(configuration.isResizable());

        applyAlignmentStyle(column,configuration.getAlignment());

        if (configuration.getCellFactory() != null) {
            column.setCellFactory(configuration.getCellFactory());
        }

        tableView.getColumns().add(column);
    }

    public void addColumn(String title, java.util.function.Function<T, ?> valueProvider) {
        addColumn(DataColumn.of(title, valueProvider));
    }

    @SafeVarargs
    public final void addColumns(DataColumn<T, ?>... columns) {
        for (DataColumn<T, ?> column : columns) {
            addColumnUnchecked(column);
        }
    }

    private void addColumnUnchecked(DataColumn<T, ?> column) {
        addColumn((DataColumn) column);
    }

    public void removeColumn(TableColumn<T, ?> column) {
        tableView.getColumns().remove(column);
    }

    public void clearColumns() {
        tableView.getColumns().clear();
    }

    // --------------------------------------------------------------------------
    // Data
    // --------------------------------------------------------------------------

    public void setItems(ObservableList<T> data) {
        tableView.setItems(data);
    }

    public void addItem(T item) {
        tableView.getItems().add(item);
    }

    public void addItems(ObservableList<T> data){
        tableView.getItems().addAll(data);
    }

    public void removeItem(T item) {
        tableView.getItems().remove(item);
    }

    public void clearItems() {
        tableView.getItems().clear();
    }

    public ObservableList<T> getItems() {
        return tableView.getItems();
    }

    // --------------------------------------------------------------------------
    // Selection
    // --------------------------------------------------------------------------

    public T getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public void select(T item) {
        tableView.getSelectionModel().select(item);
    }

    public void clearSelection() {
        tableView.getSelectionModel().clearSelection();
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        tableView.getSelectionModel().setSelectionMode(selectionMode);
    }

    public void setOnRowClick(Consumer<T> handler) {
        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2
                        && !row.isEmpty()) {

                    handler.accept(row.getItem());
                }
            });

            return row;
        });
    }

    // --------------------------------------------------------------------------
    // Empty state
    // --------------------------------------------------------------------------

    public void setEmptyMessage(String message) {
        emptyLabel.setText(message);
    }

    // --------------------------------------------------------------------------
    // Table configuration
    // --------------------------------------------------------------------------

    public TableView<T> getTableView() {
        return tableView;
    }

    public void setHeaderVisible(boolean visible) {
        tableView.setTableMenuButtonVisible(visible);
    }

    public void setFixedCellSize(double height) {
        tableView.setFixedCellSize(height);
    }

    public void refresh() {
        tableView.refresh();
    }

    // --------------------------------------------------------------------------
    // Helpers
    // --------------------------------------------------------------------------

    private void applyAlignmentStyle(TableColumn<T, ?> column, Pos alignment) {
        if (alignment == Pos.CENTER) {
            column.getStyleClass().add("align-center");
        } else if (alignment == Pos.CENTER_RIGHT || alignment == Pos.BASELINE_RIGHT || alignment == Pos.TOP_RIGHT || alignment == Pos.BOTTOM_RIGHT) {
            column.getStyleClass().add("align-right");
        }else {
            column.getStyleClass().add("align-left");
        }
    }
}
