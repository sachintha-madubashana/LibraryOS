package io.github.sachintha_madubashana.libraryos.controller.component;

import io.github.sachintha_madubashana.libraryos.model.Member;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MemberInfoCard extends VBox {

    private final Layout layout;
    private final Member member;
    private final StackPane imageContainer;
    private final ImageView imageView;

    public MemberInfoCard(Member member, Layout layout) {
        imageContainer = new StackPane();
        imageView = new ImageView();
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }

        if (layout == null) {
            throw new IllegalArgumentException("Layout cannot be null.");
        }

        this.member = member;
        this.layout = layout;

        getStyleClass().add("member-info-card");

        Label sectionLabel = new Label("MEMBER INFO");
        sectionLabel.getStyleClass().add("member-info-section-label");
        getChildren().add(sectionLabel);

        imageContainer.getStyleClass().add("member-info-image-container");
        imageView.getStyleClass().add("member-info-image");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageContainer.getChildren().add(imageView);
        imageContainer.setAlignment(Pos.CENTER);

        Label nameLabel = createLabel(member.getFullName(), "member-info-name");
        Label idLabel = createLabel(member.getId(), "member-info-id");
        Label emailLabel = createLabel(member.getEmail(), "member-info-detail");
        Label phoneLabel = createLabel(member.getPhone(), "member-info-detail");

        VBox detailsContainer = new VBox(nameLabel, idLabel, emailLabel, phoneLabel);
        detailsContainer.getStyleClass().add("member-info-details");

        if (layout == Layout.LARGE) {
            configureLargeLayout(detailsContainer);
        } else {
            configureCompactLayout(detailsContainer);
        }

    }

    private void configureLargeLayout(VBox detailsContainer) {
        getStyleClass().add("large");
        imageContainer.getStyleClass().add("large");

        imageView.fitWidthProperty().bind(imageContainer.widthProperty());
        imageView.fitHeightProperty().bind(imageContainer.heightProperty());
        VBox.setVgrow(imageContainer, Priority.ALWAYS);

        getChildren().addAll(imageContainer, detailsContainer);
    }

    private void configureCompactLayout(VBox detailsContainer) {
        getStyleClass().add("compact");
        imageContainer.getStyleClass().add("compact");

        HBox content = new HBox(10, imageContainer, detailsContainer);
        content.getStyleClass().add("member-info-compact-content");
        content.setAlignment(Pos.TOP_LEFT);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        imageContainer.setMinWidth(100);
        imageContainer.setPrefWidth(100);
        imageContainer.setMaxWidth(100);

        imageContainer.setMinHeight(100);
        imageContainer.setPrefHeight(100);
        imageContainer.setMaxHeight(100);

        HBox.setHgrow(detailsContainer, Priority.ALWAYS);
        getChildren().add(content);
    }

    private Label createLabel(String text, String styleClass) {
        Label label = new Label(text == null ? "" : text);

        label.getStyleClass().add(styleClass);
        label.setWrapText(true);

        return label;
    }

    public void setMemberImage(Image image) {
        imageView.setImage(image);
    }

    public void setMemberImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            imageView.setImage(null);
            return;
        }

        imageView.setImage(new Image(imageUrl, true));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Layout getLayout() {
        return layout;
    }

    public Member getMember() {
        return member;
    }

    public enum Layout {
        LARGE,
        COMPACT
    }
}