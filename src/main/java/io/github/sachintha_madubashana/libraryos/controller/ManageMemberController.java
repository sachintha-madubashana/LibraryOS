package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class ManageMemberController {

    @FXML
    VBox root;

    @FXML
    private DropdownSelect<String> sort;

    private ObservableList<Member> members;

    @FXML
    public void initialize() {
        sort.getItems().addAll("Member ID", "Name");
        sort.setValue("Member ID");
        generateTable();
    }

    private void generateTable() {
        DataTable<Member> table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("MEMBER ID", Member::getId).minWidth(80),
                DataColumn.of("FULL NAME", Member::getFullName).minWidth(100),
                DataColumn.of("EMAIL", Member::getEmail).minWidth(200),
                DataColumn.of("PHONE", Member::getPhone).minWidth(90),
                DataColumn.of("ADDRESS", Member::getAddress).minWidth(190),
                DataColumn.of("JOINED DATE", Member::getJoinedDate).minWidth(130),
                DataColumn.actions("ACTION", this::tableActionCell).minWidth(170)
        );

        members = FXCollections.observableArrayList(AppServices.getInstance().getMemberService().getMembers().getData());
        table.setItems(members);
        table.setSelectionMode(SelectionMode.MULTIPLE);

        Pagination pagination = new Pagination();
        pagination.setPageCount(8);
        pagination.setCurrentPage(1);

        pagination.currentPageProperty().addListener(
                (observable, oldPage, newPage) -> {
                    int page = newPage.intValue();

                    System.out.println("Current page: " + page);
                }
        );

        Label paginationLabel = new Label("Showing 6 of 6 members");
        paginationLabel.getStyleClass().add("text-caption");
        paginationLabel.setAlignment(Pos.CENTER_LEFT);
        paginationLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(paginationLabel, Priority.ALWAYS);

        HBox paginationContainer = new HBox();
        paginationContainer.setAlignment(Pos.CENTER);
        paginationContainer.getChildren().addAll(paginationLabel, pagination);

        table.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.getChildren().addAll(table, paginationContainer);
    }

    private void handleEditMember(Member member) {
        System.out.println("Edit member: " + member.getId());
    }

    private void handleDeleteMember(Member member) {
        System.out.println("Delete member: " + member.getId());
    }

    private TableCell<Member, Member> tableActionCell(TableColumn<Member, Member> column) {
        return new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            private final HBox container = new HBox(6, editButton, deleteButton);

            {
                editButton.setGraphic(new FontIcon(Feather.EDIT));
                deleteButton.setGraphic(new FontIcon(Feather.TRASH));

                editButton.getStyleClass().addAll("button", "button-outline", "button-sm");
                deleteButton.getStyleClass().addAll("button", "button-destructive", "button-sm");

                container.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Member item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    editButton.setOnAction(event -> handleEditMember(item));
                    deleteButton.setOnAction(event -> handleDeleteMember(item));

                    setGraphic(container);
                }
            }
        };

    }
}
