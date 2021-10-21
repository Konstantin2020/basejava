package com.urise.webapp.model;

public enum ContactType {
    TELEPHONE("Телефон:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN("Профиль LinkedIn:"),
    GITHUB("Профиль GitHub:"),
    STACKOVERFLOW("Профиль StackOverFlow:"),
    HOMEPAGE("Домашняя страница:");

    public String getTitle() {
        return title;
    }

    private String title;

    ContactType(String title) {
        this.title = title;
    }
}
