package io.github.sachintha_madubashana.libraryos.controller.component.table;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class DataColumn<T, V> {

    private final String title;
    private final Function<T, V> valueProvider;

    private double width = 150;
    private double minWidth = 50;
    private double maxWidth = Double.MAX_VALUE;

    private Pos alignment = Pos.CENTER_LEFT;

    private boolean sortable = true;
    private boolean resizable = true;

    private Callback<TableColumn<T, V>, TableCell<T, V>> cellFactory;

    public DataColumn(String title, Function<T, V> valueProvider) {
        this.title = title;
        this.valueProvider = valueProvider;
    }

    // ---------------------------------------------------------
    // Factory
    // ---------------------------------------------------------

    public static <T, V> DataColumn<T, V> of(String title, Function<T, V> valueProvider) {
        return new DataColumn<>(title, valueProvider);
    }

    // ---------------------------------------------------------
    // Configuration
    // ---------------------------------------------------------

    public DataColumn<T, V> width(double width) {
        this.width = width;
        return this;
    }

    public DataColumn<T, V> minWidth(double minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public DataColumn<T, V> maxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public DataColumn<T, V> alignment(Pos alignment) {
        this.alignment = alignment;
        return this;
    }

    public DataColumn<T, V> sortable(boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    public DataColumn<T, V> resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public DataColumn<T, V> cellFactory(Callback<TableColumn<T, V>, TableCell<T, V>> cellFactory) {
        this.cellFactory = cellFactory;
        return this;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public Function<T, V> getValueProvider() {
        return valueProvider;
    }

    public double getWidth() {
        return width;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public Pos getAlignment() {
        return alignment;
    }

    public boolean isSortable() {
        return sortable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public Callback<TableColumn<T, V>, TableCell<T, V>> getCellFactory() {
        return cellFactory;
    }
}
