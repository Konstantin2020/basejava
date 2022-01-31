package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
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
        for(String item : prepareList){
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
                    .append("<b>")
                    .append(organization.getHomePage().toString())
                    .append("</b>")
                    .append("</p>")
                    .append("<p>")
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
        LocalDate endDate = position.getEndDate();
        sb.append("<p>")
                .append(position.getStartDate())
                .append(" - ")
                .append((endDate.equals(DateUtil.NOW) ? "сейчас" : endDate))
                .append("</p>")
                .append("<p>")
                .append(position.getTitle())
                .append("</p>");
        String description = position.getDescription();
        if (description != null && !description.isEmpty()) {
            sb.append("<p>")
                    .append(description)
                    .append("</p>");
        }
        return sb.toString();
    }
}





