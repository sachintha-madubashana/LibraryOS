package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.model.DialogData;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;

public class AlertDialog {
    private static final int MAX_DESCRIPTION_LINES = 5;
    private static final double DESCRIPTION_LINE_HEIGHT = 20;
    private static final double COLLAPSED_HEIGHT = MAX_DESCRIPTION_LINES * DESCRIPTION_LINE_HEIGHT;
    private static final double DIALOG_SCREEN_MARGIN = 80;
    private static final double DIALOG_FIXED_HEIGHT = 200;
    private final Dialog<Boolean> dialog = new Dialog<>();
    private final VBox content = new VBox();
    private final ScrollPane descriptionScrollContainer = new ScrollPane();
    private final VBox descriptionContainer = new VBox(6);
    private final Label titleLabel = new Label();
    private final Label descriptionLabel = new Label();
    private final Button expandButton = new Button("Show more");
    private final Button cancelButton = new Button("Cancel");
    private final Button confirmButton = new Button("Confirm");
    private Type type = Type.PRIMARY;
    private FontIcon icon;
    private String title;
    private String description;
    private String cancelText = "Cancel";
    private String confirmText = "Confirm";
    private Runnable onConfirm;
    private Runnable onCancel;
    private boolean expanded;
    private boolean contentInitialized;
    private double maxDialogHeight;
    private double maxDescriptionHeight;

    public AlertDialog(Window owner, DialogData data) {
        this(owner);
        if (data != null) {
            setType(data.getType());
            setTitle(data.getTitle());
            setDescription(data.getDescription());
            setIcon(data.getIcon());
            setCancelText(data.getCancelButtonText());
            setConfirmText(data.getConfirmButtonText());
            onConfirm(data.getOnConfirm());
            onCancel(data.getOnCancel());
        }
    }

    public AlertDialog(Window owner) {
        initializeDialog(owner);
        initializeContent();
        initializeActions();
        applyType();
    }

    private void initializeDialog(Window owner) {
        calculateAvailableHeight(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);

        if (owner != null) {
            dialog.initOwner(owner);
        }

        dialog.setResizable(false);
        dialog.getDialogPane().setContent(content);
    }

    private void initializeContent() {
        content.getStyleClass().add("alert-dialog");
        content.setAlignment(Pos.CENTER);
        content.setSpacing(12);

        titleLabel.getStyleClass().add("alert-dialog-title");
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);

        descriptionLabel.getStyleClass().add("alert-dialog-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setWrapText(true);

        descriptionScrollContainer.getStyleClass().add("alert-dialog-description-scroll-container");
        descriptionScrollContainer.setFitToWidth(true);
        descriptionScrollContainer.setFitToHeight(false);
        descriptionScrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        descriptionScrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        descriptionScrollContainer.setPannable(true);
        descriptionScrollContainer.setMinViewportHeight(0);
        descriptionScrollContainer.setMaxHeight(maxDescriptionHeight);

        descriptionContainer.setAlignment(Pos.CENTER);
        descriptionContainer.setFillWidth(true);

        expandButton.getStyleClass().add("alert-dialog-expand-button");
        expandButton.setVisible(false);
        expandButton.setManaged(false);
        expandButton.setOnAction(event -> toggleDescription());

        cancelButton.getStyleClass().add("alert-dialog-cancel-button");
        confirmButton.getStyleClass().add("alert-dialog-confirm-button");

        contentInitialized = true;
        rebuildContent();
    }

    private void initializeActions() {
        cancelButton.setOnAction(event -> {
            dialog.setResult(false);
            if (onCancel != null) {
                onCancel.run();
            }
            dialog.close();
        });

        confirmButton.setOnAction(event -> {
            if (onConfirm != null) {
                onConfirm.run();
            }
            dialog.setResult(true);
            dialog.close();
        });
    }

    private void rebuildContent() {
        if (!contentInitialized) {
            return;
        }

        content.getChildren().clear();

        if (icon != null) {
            VBox iconContainer = new VBox(icon);

            iconContainer.getStyleClass().add("alert-dialog-icon-container");
            iconContainer.setAlignment(Pos.CENTER);
            content.getChildren().add(iconContainer);
        }

        titleLabel.setText(title == null ? "" : title);
        content.getChildren().add(titleLabel);
        descriptionContainer.getChildren().clear();

        if (description != null && !description.isBlank()) {
            descriptionLabel.setText(description);
            descriptionContainer.getChildren().add(descriptionLabel);

            updateDescriptionVisibility();

            descriptionScrollContainer.contentProperty().set(descriptionContainer);
            content.getChildren().add(descriptionScrollContainer);
            content.getChildren().add(expandButton);
        }

        HBox footer = createFooter();
        content.getChildren().add(footer);
        VBox.setMargin(footer, new Insets(8, -20, -20, -20));

        dialog.getDialogPane().setContent(content);
        Platform.runLater(this::updateExpandButtonVisibility);

        applyType();
    }

    private HBox createFooter() {
        HBox footer = new HBox(8);

        footer.getStyleClass().add("alert-dialog-footer");
        footer.setAlignment(Pos.CENTER);
        footer.setFillHeight(true);

        cancelButton.setText(cancelText);
        confirmButton.setText(confirmText);

        cancelButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        HBox.setHgrow(confirmButton, Priority.ALWAYS);

        footer.getChildren().setAll(cancelButton, confirmButton);

        return footer;
    }

    private void applyType() {
        content.getStyleClass().removeAll("alert-dialog-primary", "alert-dialog-destructive");

        if (type == Type.DESTRUCTIVE) {
            content.getStyleClass().add("alert-dialog-destructive");
            confirmButton.getStyleClass().remove("button-primary");
            confirmButton.getStyleClass().add("button-destructive");

        } else {
            content.getStyleClass().add("alert-dialog-primary");
            confirmButton.getStyleClass().remove("button-destructive");
            confirmButton.getStyleClass().add("button-primary");
        }
    }

    private void toggleDescription() {
        expanded = !expanded;
        updateDescriptionVisibility();
    }

    private void updateDescriptionVisibility() {
        if (description == null || description.isBlank()) {
            expandButton.setVisible(false);
            expandButton.setManaged(false);

            descriptionScrollContainer.setVisible(false);
            descriptionScrollContainer.setManaged(false);
            return;
        }

        descriptionScrollContainer.setVisible(true);
        descriptionScrollContainer.setManaged(true);

        descriptionScrollContainer.setPrefViewportHeight(
                expanded
                        ? maxDescriptionHeight
                        : COLLAPSED_HEIGHT
        );

        descriptionScrollContainer.setMaxHeight(
                expanded
                        ? maxDescriptionHeight
                        : COLLAPSED_HEIGHT
        );

        expandButton.setText(expanded ? "Show less" : "Show more");
        updateExpandButtonVisibility();
    }

    private void updateExpandButtonVisibility() {
        Platform.runLater(() -> {
            if (!dialog.isShowing()) {
                return;
            }

            descriptionScrollContainer.applyCss();
            descriptionContainer.applyCss();
            descriptionContainer.layout();

            double contentHeight = descriptionContainer.getBoundsInLocal().getHeight();
            boolean canExpand = contentHeight > COLLAPSED_HEIGHT + 1;

            expandButton.setVisible(canExpand);
            expandButton.setManaged(canExpand);

            if (!canExpand) {
                expanded = false;
                descriptionScrollContainer.setPrefViewportHeight(contentHeight);
            }
        });
    }

    private void calculateAvailableHeight(Window owner) {
        Rectangle2D bounds;
        if (owner != null) {
            var screens = Screen.getScreensForRectangle(
                    owner.getX(),
                    owner.getY(),
                    owner.getWidth(),
                    owner.getHeight()
            );
            bounds = screens.isEmpty()
                    ? Screen.getPrimary().getVisualBounds()
                    : screens.get(0).getVisualBounds();

        } else {
            bounds = Screen.getPrimary().getVisualBounds();
        }

        maxDialogHeight = bounds.getHeight() - DIALOG_SCREEN_MARGIN;

        maxDescriptionHeight = Math.max(
                COLLAPSED_HEIGHT,
                maxDialogHeight - DIALOG_FIXED_HEIGHT
        );
    }

    // Configuration
    public AlertDialog setType(Type type) {
        this.type = type == null ? Type.PRIMARY : type;
        applyType();
        return this;
    }

    public AlertDialog setTitle(String title) {
        this.title = title;
        if (contentInitialized) {
            titleLabel.setText(title == null ? "" : title);
        }
        return this;
    }

    public AlertDialog setDescription(String description) {
        this.description = description;
        this.expanded = false;
        rebuildContent();
        return this;
    }

    public AlertDialog setIcon(FontIcon icon) {
        this.icon = icon;
        rebuildContent();
        return this;
    }

    public AlertDialog setCancelText(String text) {
        this.cancelText = text == null ? "Cancel" : text;
        cancelButton.setText(this.cancelText);
        return this;
    }

    public AlertDialog setConfirmText(String text) {
        this.confirmText = text == null ? "Confirm" : text;
        confirmButton.setText(this.confirmText);
        return this;
    }

    public AlertDialog onConfirm(Runnable action) {
        this.onConfirm = action;
        return this;
    }

    public AlertDialog onCancel(Runnable action) {
        this.onCancel = action;
        return this;
    }

    public boolean showAndWait() {
        rebuildContent();
        return dialog.showAndWait().orElse(false);
    }

    // Display
    public void show() {
        rebuildContent();
        dialog.show();
    }

    public void close() {
        dialog.close();
    }

    public enum Type {
        PRIMARY,
        DESTRUCTIVE
    }
}
