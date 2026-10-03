package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.model.Member;
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

        List<Member> members = new ArrayList<>();
        members.add(new Member("MB001", "John Smith", "john.smith@email.com", "0771234567", "25 Main Street, Colombo", LocalDate.of(2026, 1, 15)));
        members.add(new Member("MB002", "Emily Johnson", "emily.johnson@email.com", "0712345678", "14 Park Road, Kandy", LocalDate.of(2026, 2, 3)));
        members.add(new Member("MB003", "Michael Brown", "michael.brown@email.com", "0763456789", "8 Lake View, Galle", LocalDate.of(2026, 2, 18)));
        members.add(new Member("MB004", "Sarah Wilson", "sarah.wilson@email.com", "0754567890", "42 Hill Street, Negombo", LocalDate.of(2026, 3, 7)));
        members.add(new Member("MB005", "David Miller", "david.miller@email.com", "0705678901", "19 Station Road, Matara", LocalDate.of(2026, 3, 21)));
        members.add(new Member("MB006", "Jessica Davis", "jessica.davis@email.com", "0776789012", "31 Flower Road, Colombo", LocalDate.of(2026, 4, 2)));
        members.add(new Member("MB007", "Daniel Anderson", "daniel.anderson@email.com", "0717890123", "7 Temple Road, Kurunegala", LocalDate.of(2026, 4, 15)));
        members.add(new Member("MB008", "Sophia Taylor", "sophia.taylor@email.com", "0768901234", "55 Beach Road, Kalutara", LocalDate.of(2026, 5, 1)));
        members.add(new Member("MB009", "James Thomas", "james.thomas@email.com", "0759012345", "12 Main Street, Jaffna", LocalDate.of(2026, 5, 19)));
        members.add(new Member("MB010", "Olivia Moore", "olivia.moore@email.com", "0700123456", "28 Garden Avenue, Colombo", LocalDate.of(2026, 6, 5)));
        members.add(new Member("MB011", "William Martin", "william.martin@email.com", "0771122334", "16 Church Road, Kandy", LocalDate.of(2026, 6, 17)));
        members.add(new Member("MB012", "Emma Jackson", "emma.jackson@email.com", "0712233445", "9 Riverside Road, Gampaha", LocalDate.of(2026, 7, 4)));

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
}
