package io.github.sachintha_madubashana.libraryos.controller.component;

public interface InfoCard {
    LayoutType getLayout();
    void configureLayout();

    enum LayoutType {
        LARGE,
        COMPACT
    }
}
