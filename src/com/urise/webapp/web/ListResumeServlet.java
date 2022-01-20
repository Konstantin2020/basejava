package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class ListResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    public void init() {
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> mapResumes = new LinkedHashMap<>();
        for (Resume r : sqlStorage.getAllSorted()) {
            mapResumes.put(r.getUuid(), r.getFullName());
        }
        request.setAttribute("mapResumes", mapResumes);
        getServletContext().getRequestDispatcher("/list.jsp").forward(request, response);
    }
}
