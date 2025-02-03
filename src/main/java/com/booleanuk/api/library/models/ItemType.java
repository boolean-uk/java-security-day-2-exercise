package com.booleanuk.api.library.models;

public enum ItemType {
    BOOK, CD, DVD, VIDEO_GAME, BOARD_GAME;

    @Override
    public String toString() {
        return switch (this) {
            case BOOK -> "book";
            case CD -> "CD";
            case DVD -> "DVD";
            case VIDEO_GAME -> "video game";
            case BOARD_GAME -> "board game";
            default -> this.name();
        };
    }
}
