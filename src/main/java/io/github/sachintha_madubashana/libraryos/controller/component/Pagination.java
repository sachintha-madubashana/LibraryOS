package io.github.sachintha_madubashana.libraryos.controller.component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class Pagination extends HBox {

    private static final String STYLE_CLASS = "pagination";
    private static final String PREVIOUS_STYLE_CLASS = "pagination-previous";
    private static final String NEXT_STYLE_CLASS = "pagination-next";
    private static final String PAGE_STYLE_CLASS = "pagination-page";
    private static final String ACTIVE_STYLE_CLASS = "active";
    private static final String ELLIPSIS_STYLE_CLASS = "pagination-ellipsis";

    private final IntegerProperty currentPage =
            new SimpleIntegerProperty(this, "currentPage", 1);

    private final IntegerProperty pageCount =
            new SimpleIntegerProperty(this, "pageCount", 1);

    private final IntegerProperty maxVisiblePages =
            new SimpleIntegerProperty(this, "maxVisiblePages", 5);

    private final Button previousButton = new Button();
    private final Button nextButton = new Button();

    private final HBox pageContainer = new HBox();

    public Pagination() {
        initialize();
        registerListeners();
        refresh();
    }

    public Pagination(int pageCount) {
        this();
        setPageCount(pageCount);
    }

    public Pagination(int pageCount, int currentPage) {
        this();
        setPageCount(pageCount);
        setCurrentPage(currentPage);
    }

    private void initialize() {
        getStyleClass().add(STYLE_CLASS);

        setAlignment(Pos.CENTER);

        previousButton.getStyleClass().add(PREVIOUS_STYLE_CLASS);
        nextButton.getStyleClass().add(NEXT_STYLE_CLASS);

        previousButton.setText("Previous");
        FontIcon previousIcon = new FontIcon("fth-chevron-left");
        previousIcon.setIconSize(14);
        previousButton.setGraphic(previousIcon);
        previousButton.setAlignment(Pos.CENTER);

        nextButton.setText("Next");
        FontIcon nextIcon = new FontIcon("fth-chevron-right");
        nextIcon.setIconSize(14);
        nextButton.setGraphic(nextIcon);
        nextButton.setAlignment(Pos.CENTER);
        nextButton.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        previousButton.setFocusTraversable(false);
        nextButton.setFocusTraversable(false);

        pageContainer.getStyleClass().add("pagination-pages");
        pageContainer.setAlignment(Pos.CENTER);

        getChildren().addAll(previousButton, pageContainer, nextButton);

        previousButton.setOnAction(event -> previousPage());

        nextButton.setOnAction(event -> nextPage());
    }

    private void registerListeners() {
        currentPage.addListener((observable, oldValue, newValue) -> refresh());

        pageCount.addListener((observable, oldValue, newValue) -> {
            if (getCurrentPage() > getPageCount()) {
                setCurrentPage(getPageCount());
            }

            refresh();
        });

        maxVisiblePages.addListener((observable, oldValue, newValue) -> refresh());
    }

    private void refresh() {
        pageContainer.getChildren().clear();

        int current = getCurrentPage();
        int total = getPageCount();
        int maxButtons = getMaxVisiblePages();

        previousButton.setDisable(current <= 1);
        nextButton.setDisable(current >= total);

        if (total <= maxButtons) {
            for (int page = 1; page <= total; page++) {
                addPageButton(page);
            }
        } else {
            addDynamicPageButtons(current, total, maxButtons);
        }
    }

    private void addDynamicPageButtons(int currentPage, int total, int maxButtons) {
        if (maxButtons < 3) {
            maxButtons = 3;
        }

        addPageButton(1);

        if (total <= maxButtons) {
            for (int page = 2; page <= total; page++) {
                addPageButton(page);
            }
            return;
        }

        int middleButtons = maxButtons - 2;

        int start = currentPage - (middleButtons / 2);
        int end = start + (middleButtons - 1);

        if (start < 2) {
            start = 2;
            end = start + (middleButtons - 1);
        }

        if (end > (total - 1)) {
            end = total - 1;
            start = (end - middleButtons) + 1;
        }

        if (start > 2) {
            addEllipsis();
        }

        for (int page = start; page <= end; page++) {
            addPageButton(page);
        }

        if (end < total - 1) {
            addEllipsis();
        }

        addPageButton(total);
    }

    private void addPageButton(int page) {
        Button button = new Button(String.valueOf(page));

        button.getStyleClass().add(PAGE_STYLE_CLASS);

        button.setFocusTraversable(false);

        if (page == getCurrentPage()) {
            button.getStyleClass().add(ACTIVE_STYLE_CLASS);
        }

        button.setOnAction(event -> setCurrentPage(page));

        pageContainer.getChildren().add(button);
    }

    private void addEllipsis() {
        Label ellipsis = new Label("...");

        ellipsis.getStyleClass().add(ELLIPSIS_STYLE_CLASS);

        pageContainer.getChildren().add(ellipsis);
    }

    // -------------------------------------------------------------------------
    // Current Page
    // -------------------------------------------------------------------------

    public final int getCurrentPage() {
        return currentPage.get();
    }

    public final void setCurrentPage(int page) {
        if (page < 1) {
            page = 1;
        }

        if (page > getPageCount()) {
            page = getPageCount();
        }

        if (getCurrentPage() != page) {
            currentPage.set(page);
        }
    }

    public final IntegerProperty currentPageProperty() {
        return currentPage;
    }

    // -------------------------------------------------------------------------
    // Page Count
    // -------------------------------------------------------------------------

    public final int getPageCount() {
        return pageCount.get();
    }

    public final void setPageCount(int count) {
        if (count < 1) {
            count = 1;
        }

        pageCount.set(count);
    }

    public final IntegerProperty pageCountProperty() {
        return pageCount;
    }

    // -------------------------------------------------------------------------
    // Maximum Page Buttons
    // -------------------------------------------------------------------------

    public final int getMaxVisiblePages() {
        return maxVisiblePages.get();
    }

    public final void setMaxVisiblePages(int count) {
        if (count < 3) {
            count = 3;
        }

        maxVisiblePages.set(count);
    }

    public final IntegerProperty maxVisiblePagesProperty() {
        return maxVisiblePages;
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    public final void nextPage() {
        if (getCurrentPage() < getPageCount()) {
            setCurrentPage(getCurrentPage() + 1);
        }
    }

    public final void previousPage() {
        if (getCurrentPage() > 1) {
            setCurrentPage(getCurrentPage() - 1);
        }
    }

    public final boolean isFirstPage() {
        return getCurrentPage() == 1;
    }

    public final boolean isLastPage() {
        return getCurrentPage() == getPageCount();
    }
}
