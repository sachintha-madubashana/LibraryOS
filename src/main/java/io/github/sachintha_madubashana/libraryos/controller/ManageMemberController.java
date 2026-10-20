package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManageMemberController {

    @FXML
    VBox root;

    @FXML
    private DropdownSelect<String> sort;

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
                DataColumn.of("MEMBER ID", Member::getId).width(100),
                DataColumn.of("FULL NAME", Member::getFullName).width(180),
                DataColumn.of("EMAIL", Member::getEmail).width(220),
                DataColumn.of("PHONE", Member::getPhone).width(140),
                DataColumn.of("ADDRESS", Member::getAddress).width(220),
                DataColumn.of("JOINED DATE", Member::getJoinedDate).width(130),
                DataColumn.actions("ACTION", 170, this::handleEditMember, this::handleDeleteMember)
        );

        table.setItems(AppServices.getInstance().getMemberService().getMembers());
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
}
