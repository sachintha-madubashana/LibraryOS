package io.github.sachintha_madubashana.libraryos.controller;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.controller.component.AutocompletePopup;
import io.github.sachintha_madubashana.libraryos.controller.component.DropdownSelect;
import io.github.sachintha_madubashana.libraryos.controller.component.InputField;
import io.github.sachintha_madubashana.libraryos.controller.component.Pagination;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataColumn;
import io.github.sachintha_madubashana.libraryos.controller.component.table.DataTable;
import io.github.sachintha_madubashana.libraryos.event.NotificationEvent;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.Notification;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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

import java.util.List;
import java.util.function.Function;

public class ManageMemberController {

    @FXML
    VBox root;

    @FXML
    InputField searchField;

    @FXML
    Button searchBtn;

    @FXML
    private DropdownSelect<String> sort;

    private ObservableList<Member> members;

    private AutocompletePopup<Member> popup;

    private List<Member> filtered;

    @FXML
    public void initialize() {
        members = FXCollections.observableArrayList();
        popup = new AutocompletePopup<>();

        sort.getItems().addAll(SortingValue.ID_ASCENDING.getDisplayName(), SortingValue.ID_DESCENDING.getDisplayName(), SortingValue.NAME_ASCENDING.getDisplayName(), SortingValue.NAME_DESCENDING.getDisplayName());
        sort.setValue(SortingValue.ID_ASCENDING.getDisplayName());
        generateTable();

        loadMembers();
        initiateButtonActions();
    }

    public void initiateButtonActions() {
        searchField.setOnInputChange((obs, oldValue, newValue) -> {
            search(newValue);
        });
        searchBtn.setOnAction(event -> showSearchedResult(null));
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                showSearchedResult(null);
            }
        });
        popup.setOnSuggestionSelected(suggestion -> {
            searchField.setInputValue(suggestion.toString());
            showSearchedResult(suggestion);
        });
        searchField.focusedProperty().addListener((observable, oldValue, isFocused) -> {
            if (Boolean.TRUE.equals(isFocused) && filtered != null && !filtered.isEmpty() && !popup.isShowing() && !searchField.getInputValue().isBlank()) {
                popup.showBelow(searchField);
            }
        });
        sort.setOnSelectionChanged(event -> {
            loadMembers();
        });
    }

    private void loadMembers() {
        members.clear();
        Result<List<Member>> result = AppServices.getInstance().getMemberService().getMembers(null, SortingValue.getSortingValue(sort.getValue()));
        if (result.isError()) {
            Platform.runLater(() -> root.fireEvent(new NotificationEvent(new Notification(result.getMessage(), result.getDescription(), Notification.Type.ALERT_ERROR))));
            return;
        }
        members.addAll(result.getData());
    }

    private void search(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            loadMembers();
            popup.hide();
            return;
        }

        filtered = AppServices.getInstance().getMemberService().getMembers(searchTerm, SortingValue.getSortingValue(sort.getValue())).getData();
        popup.setSuggestions(filtered);

        if (!filtered.isEmpty()) {
            popup.showBelow(searchField);
        } else {
            popup.hide();
        }

    }

    private void showSearchedResult(Member selectedMember) {
        String value = searchField.getInputValue();
        if (value.isBlank()) {
            root.fireEvent(new NotificationEvent(new Notification("Empty search query", "The search query cannot be empty.", Notification.Type.ALERT_WARNING)));
            return;
        }

        if (selectedMember != null) {
            members.setAll(selectedMember);
            return;
        }

        if (filtered == null || filtered.isEmpty()) {
            root.fireEvent(new NotificationEvent(new Notification("No results found", "No members found: " + value, Notification.Type.ALERT_INFO)));
            return;
        }

        members.setAll(filtered);
    }

    private void generateTable() {
        DataTable<Member> table = new DataTable<>();
        table.setMinHeight(360);

        table.addColumns(
                DataColumn.of("MEMBER ID", ((Function<Member, String>) Member::getCode).andThen(String::toUpperCase)).minWidth(80),
                DataColumn.of("FULL NAME", Member::getFullName).minWidth(100),
                DataColumn.of("EMAIL", Member::getEmail).minWidth(200),
                DataColumn.of("PHONE", Member::getPhone).minWidth(90),
                DataColumn.of("ADDRESS", Member::getAddress).minWidth(190),
                DataColumn.of("JOINED DATE", Member::getJoinedDate).minWidth(130),
                DataColumn.actions("ACTION", this::tableActionCell).minWidth(170)
        );
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

        Label paginationLabel = new Label();
        paginationLabel.getStyleClass().add("text-caption");
        paginationLabel.textProperty().bind(Bindings.size(members).asString("Showing %d members"));
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
