package com.pablozoani.domain;

public enum State {
    CREATED("Created"),
    ORDERED("Ordered"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
