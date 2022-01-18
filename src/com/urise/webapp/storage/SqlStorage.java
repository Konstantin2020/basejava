package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps ->
        {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?;")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                deleteContacts(conn, resume);
                deleteSections(conn, resume);
                insertContacts(conn, resume);
                insertSections(conn, resume);
                return null;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) " +
                    "VALUES ( ?, ? )")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
                insertContacts(conn, resume);
                insertSections(conn, resume);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r" +
                        " LEFT JOIN contact c" +
                        " ON r.uuid = c.resume_uuid " +
                        " LEFT JOIN section s" +
                        " ON r.uuid = s.resume_uuid " +
                        " WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContactFromDb(resume, rs);
                        addSectionFromDb(resume, rs);
                    } while (rs.next());
                    return resume;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps ->
        {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

  /*  @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM resume r " +
                                    "LEFT JOIN contact c " +
                                    "ON r.uuid = c.resume_uuid ORDER BY full_name, uuid;")) {
                        Map<String, Resume> map = new LinkedHashMap<>();
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid");
                            String full_name = rs.getString("full_name");
                            if (!map.containsKey(uuid)) {
                                map.put(uuid, new Resume(uuid, full_name));
                            }
                            addContactFromDb(map.get(uuid), rs);
                        }
                        return new ArrayList<>(map.values());
                    }
                }
        );
    }*/

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn ->
                new ArrayList<>(resultSections(conn, resultContacts(conn, resultResume(conn))).values()));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

/*Приватные методы следует располагать в конце класса по мере их вызова из публичных методов.
Располагать приватный метод среди публичных допускается только в одном случае:
только сразу публичного, если этот приватный метод только в нём используется.*/

    private void addContactFromDb(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSectionFromDb(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("section_type");
        SectionType sectionType = SectionType.valueOf(type);
        String value = rs.getString("section_value");
        if (value != null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ArrayList<String> resultList = new ArrayList<>();
                    ArrayList<String> prepareList = (ArrayList<String>) Stream.of(value.split("\n")).collect(Collectors.toList());
                    for (String rec : prepareList) {
                        if (rec.indexOf("-") == 0) {
                            StringBuilder sb = new StringBuilder(rec);
                            sb.deleteCharAt(0);
                            resultList.add(sb.toString());
                        } else {
                            resultList.add(rec);
                        }
                    }
                    resume.addSection(sectionType, new ListSection(resultList));
                    break;
            }
        }
    }

    private Map<String, Resume> resultResume(Connection conn) throws SQLException {
        return sqlHelper.executeWithConn(conn,
                "    SELECT * FROM resume " +
                        "ORDER BY full_name, uuid;",
                (ps) -> {
                    Map<String, Resume> map = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        map.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return map;
                });
    }

    private Map<String, Resume> resultContacts(Connection conn, Map<String, Resume> map) throws SQLException {
        return sqlHelper.executeWithConn(conn,
                "    SELECT * FROM contact ",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addContactFromDb(map.get(rs.getString("resume_uuid")), rs);
                    }
                    return map;
                });
    }

    private Map<String, Resume> resultSections(Connection conn, Map<String, Resume> map) throws SQLException {
        return sqlHelper.executeWithConn(conn,
                "    SELECT * FROM section ",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addSectionFromDb(map.get(rs.getString("resume_uuid")), rs);
                    }
                    return map;
                });
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        sqlHelper.executeWithConn(conn, "DELETE FROM contact WHERE resume_uuid = ?;", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        sqlHelper.executeWithConn(conn, "DELETE FROM section WHERE resume_uuid = ?;", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        sqlHelper.executeWithConn(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES ( ?, ? ,?)", ps ->
        {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        sqlHelper.executeWithConn(conn, "INSERT INTO section (resume_uuid, section_type, " +
                "section_value) VALUES ( ?, ? ,?)", ps ->
        {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                switch (SectionType.valueOf(e.getKey().name())) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, e.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String stringList = Stream.of(e.getValue()).map(String::valueOf).collect(Collectors.joining("/n"));
                        ps.setString(3, stringList);
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }
}
