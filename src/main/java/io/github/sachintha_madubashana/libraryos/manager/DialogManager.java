package io.github.sachintha_madubashana.libraryos.manager;

import io.github.sachintha_madubashana.libraryos.controller.component.AlertDialog;
import io.github.sachintha_madubashana.libraryos.model.DialogData;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Window;

public class DialogManager {
    private final Node root;

    public DialogManager(Node rootNode) {
        this.root = rootNode;
    }

    public boolean showDialog(DialogData dialogData) {
        Window window = null;
        if (root != null && root.getScene() != null) {
            window = root.getScene().getWindow();
        }

        AlertDialog dialog = new AlertDialog(window, dialogData);
        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        if (root != null) {
            root.setEffect(blurEffect);
        }
        boolean confirmed = dialog.showAndWait();
        if (root != null) {
            root.setEffect(null);
        }
        return confirmed;
    }

}
