package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private String title;
    private final String description;

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Position(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        //Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return startDate.equals(position.startDate) && endDate.equals(position.endDate) && title.equals(position.title) && Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        Object dateFinish = endDate;
        Object isDescription = description;
        if (dateFinish == null) {
            dateFinish = "по настоящее время.";
        }
        if (isDescription == null) {
            isDescription = "";
        }
        return startDate + " - " + dateFinish + "\n" + title + "\n" + isDescription + "\n";
    }
}