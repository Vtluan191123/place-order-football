package com.vtluan.variable;

public enum NameDirectory {
    USER_IMAGES("user-images"),
    REVIEW_IMAGES("review-images");

    private final String directoryName;

    // Constructor
    NameDirectory(String directoryName) {
        this.directoryName = directoryName;
    }

    // Getter
    public String getDirectoryName() {
        return directoryName;
    }

}
