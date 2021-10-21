package com.urise.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    List<String> lines;

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append("-").append(line).append('\n');
        }
        return sb.toString();
    }
}