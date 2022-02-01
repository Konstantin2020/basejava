package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JspHelper {

    public static String toJspSection(SectionType sectionType, AbstractSection abstractSection) {
        return abstractSection == null ? "" : toJspFromType(sectionType, abstractSection, true);
    }

    public static String toJspMap(Map.Entry<SectionType, AbstractSection> sectionEntry, boolean isEditable) {
        AbstractSection abstractSection = sectionEntry.getValue();
        return abstractSection == null ? "" : toJspFromType(sectionEntry.getKey(), abstractSection, isEditable);
    }

    public static String toJspFromType(SectionType sectionType, AbstractSection abstractSection, boolean isEditable) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return getTextSection((TextSection) abstractSection);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return isEditable ? toEditListSection((ListSection) abstractSection)
                        : toViewListSection((ListSection) abstractSection);
            case EXPERIENCE:
            case EDUCATION:
                return isEditable ? toEditOrganizationSection((OrganizationSection) abstractSection)
                        : toViewOrganizationSection((OrganizationSection) abstractSection);
            default:
                return "Resume has no sections!";
        }
    }

    private static String getTextSection(TextSection textSection) {
        return textSection.getContent();
    }

    private static String toEditListSection(ListSection listSection) {
        List<String> prepareList = listSection.getItems();
        List<String> resultList = new ArrayList<>();
        for (String item : prepareList) {
            if (item != null && item.trim().length() != 0) {
                resultList.add(item);
            }
        }
        return String.join("\n", resultList);
    }

/*    private static String toViewListSection(ListSection listSection) {
        return String.join("</br>", listSection.getItems());
    }*/

    private static String toViewListSection(ListSection listSection) {
        List<String> list = listSection.getItems();
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> {
            sb.append("<ul>")
                    .append("<li>")
                    .append(item)
                    .append("</li>")
                    .append("</p>")
                    .append("</ul>");
        });
        return sb.toString();
    }

    private static String toEditOrganizationSection(OrganizationSection organizationSection) {
        return "";
    }

    private static String toViewOrganizationSection(OrganizationSection organizationSection) {
        List<Organization> list = organizationSection.getOrganizations();
        StringBuilder sb = new StringBuilder();
        list.forEach(organization -> {
            sb.append("<p>")
                    .append("<b>");
            if (organization.getHomePage().getUrl() == null || organization.getHomePage().getUrl().isEmpty()) {
                sb.append("<p>")
                        .append(organization.getHomePage().getName())
                        .append("</b>")
                        .append("</p>");
            } else {
                sb.append("<a href=")
                        .append(organization.getHomePage().getUrl())
                        .append(">")
                        .append(organization.getHomePage().getName())
                        .append("</a>")
                        .append("</b>")
                        .append("</p>");
            }
            sb.append("<p>")
                    .append("<ul>");
            organization.getPositions()
                    .forEach(position ->
                            sb.append("<li>")
                                    .append(toViewPosition(position))
                                    .append("</li>"));
            sb.append("</ul>")
                    .append("</p>");
        });
        return sb.toString();
    }

    private static String toViewPosition(Organization.Position position) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"100%\" cellpadding=\"10\">")
                .append("<tr>")
                .append("<td width=\"20%\">")
                .append(formatDates(position))
                .append("</td>")
                .append("<td>")
                .append(position.getTitle())
                .append("</td>")
                .append("</tr>");
        String description = position.getDescription();
        if (description != null && !description.isEmpty()) {
            sb.append("<tr>")
                    .append("<td>")
                    .append(" ")
                    .append("</td>")
                    .append("<td>")
                    .append(description)
                    .append("</td>")
                    .append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }
}





