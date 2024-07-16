package com.rebook.image.domain;

public class Directory {
    private String[] paths;

    public Directory(String... paths) {
        this.paths = paths;
    }

    public String getFullPath(String fileName) {
        return String.join("/", paths) + "/" + fileName;
    }
}
