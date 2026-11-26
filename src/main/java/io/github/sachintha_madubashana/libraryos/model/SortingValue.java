package io.github.sachintha_madubashana.libraryos.model;

public enum SortingValue {
    ID_ASCENDING("ID A-Z"),
    ID_DESCENDING("ID Z-A"),
    NAME_ASCENDING("Name A-Z"),
    NAME_DESCENDING("Name Z-A");

    private final String displayName;

    SortingValue(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static SortingValue getSortingValue(String displayName) {
        for(SortingValue sortingValue : SortingValue.values()) {
            if(sortingValue.getDisplayName().equals(displayName)) {
                return sortingValue;
            }
        }
        throw new IllegalArgumentException("Invalid sorting value: " + displayName);
    }
}
