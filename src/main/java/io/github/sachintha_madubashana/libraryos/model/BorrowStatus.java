package io.github.sachintha_madubashana.libraryos.model;

public enum BorrowStatus {
    BORROWED(1),
    RETURNED(2),
    OVERDUE(3);

    private final int value;

    BorrowStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BorrowStatus fromString(String status) {
        if (status != null) {
            for (BorrowStatus b : BorrowStatus.values()) {
                if (status.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + status + " found");
    }
}
