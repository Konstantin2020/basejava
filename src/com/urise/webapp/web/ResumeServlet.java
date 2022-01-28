package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "save":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = "".equals(uuid) ? new Resume(fullName) : storage.get(uuid);
        saveContacts(request, resume);
        saveSections(request, resume);
        if ("".equals(fullName)) {
            request.setAttribute("resume", resume);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                    .forward(request, response);
            return;
        }
        if ("".equals(uuid)) {
            storage.save(resume);
        } else {
            resume.setFullName(fullName);
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private void saveContacts(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
    }

    private void saveSections(HttpServletRequest request, Resume resume) {
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, getTextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, getListSection(value));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection orgSection = getOrganizationSection(request, type);
                        if (orgSection == null) {
                            resume.getSections().remove(type);
                        } else {
                            resume.addSection(type, orgSection);
                        }
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
    }

    private TextSection getTextSection(String value) {
        return new TextSection(value);
    }

    private ListSection getListSection(String value) {
        return new ListSection(Arrays.asList(value.replaceAll("</br>", "")
                .split("\\s*\\r\\n\\s*")));
    }

    private OrganizationSection getOrganizationSection(HttpServletRequest request, SectionType type) {
        List<Organization> organizations = new ArrayList<>();
        String[] organizationNames = request.getParameterValues(type.name() + "organizationName");
        String[] organizationUrls = request.getParameterValues(type.name() + "url");
        for (int i = 0; i < organizationNames.length; i++) {
            String organizationName = organizationNames[i];
            String organizationUrl = organizationUrls[i];
            if (isReal(organizationName)) {
                List<Organization.Position> positionsFromJsp = getPositionsFromJsp(
                        request.getParameterValues(type.name() + i + "startDate"),
                        request.getParameterValues(type.name() + i + "endDate"),
                        request.getParameterValues(type.name() + i + "title"),
                        request.getParameterValues(type.name() + i + "description"));
                if (positionsFromJsp.size() != 0) {
                    organizations.add(new Organization(new Link(organizationName, organizationUrl), positionsFromJsp));
                }
            }
        }
        return organizations.size() == 0 ? null : new OrganizationSection(organizations);
    }

    private List<Organization.Position> getPositionsFromJsp(String[] startDates, String[] endDates, String[] titles,
                                                            String[] descriptions) {
        List<Organization.Position> positionsFromJsp = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            if (isReal(titles[i]) && isReal(startDates[i])) {
                String description = descriptions == null ? null : descriptions[i];
                Organization.Position positionFromJsp = new Organization.Position(
                        checkDate(startDates[i]), checkDate(endDates[i]), titles[i], description);
                positionsFromJsp.add(positionFromJsp);
            }
        }
        return positionsFromJsp;
    }

    private boolean isReal(String str) {
        return str != null && !str.isEmpty();
    }

    private LocalDate checkDate(String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }

}
