package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, Link> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), s -> {
                dos.writeUTF(s.getKey().name());
                writeLink(dos, s.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), s -> {
                dos.writeUTF(s.getKey().name());
                AbstractSection as = s.getValue();
                switch (s.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) as).getContent());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection(dos, (OrganizationSection) as);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dos, (ListSection) as);
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), readLink(dis)));
            readWithException(dis, () -> {
                        SectionType sectionType = SectionType.valueOf(dis.readUTF());
                        switch (sectionType) {
                            case PERSONAL:
                            case OBJECTIVE:
                                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                                break;
                            case EDUCATION:
                            case EXPERIENCE:
                                readOrganizationSection(dis, resume, sectionType);
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                readListSection(dis, resume, sectionType);
                                break;
                        }
                    }
            );
        }
        return resume;
    }

    private void writeNullSafely(DataOutputStream dos, String str) throws IOException {
        dos.writeUTF(str != null ? str : "");
    }

    private String readNullIfEmpty(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return str.equals("") ? null : str;
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getName());
        writeNullSafely(dos, link.getUrl());
    }

    private Link readLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), readNullIfEmpty(dis));
    }

    private void writeListSection(DataOutputStream dos, ListSection as) throws IOException {
        List<String> list = new ArrayList<>(as.getItems());
        int size = list.size();
        dos.writeInt(list.size());
        for (String s : list) {
            dos.writeUTF(s);
        }
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        List<String> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection as) throws IOException {
        List<Organization> list = new ArrayList<>(as.getOrganizations());
        int size = list.size();
        dos.writeInt(list.size());
        for (Organization organization : list) {
            writeOrganization(dos, organization);
        }
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        List<Organization> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(readOrganization(dis));
        }
        resume.addSection(sectionType, new OrganizationSection(list));
    }

    private void writeOrganization(DataOutputStream dos, Organization org) throws IOException {
        writeLink(dos, org.getHomePage());
        List<Organization.Position> list = new ArrayList<>(org.getPositions());
        int size = list.size();
        dos.writeInt(list.size());
        for (Organization.Position position : list) {
            writePosition(dos, position);
        }
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Link homePage = readLink(dis);
        List<Organization.Position> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(readPosition(dis));
        }
        return new Organization(homePage, list);
    }


    private void writePosition(DataOutputStream dos, Organization.Position pos) throws IOException {
        writeLocalDate(dos, pos.getStartDate());
        writeLocalDate(dos, pos.getEndDate());
        dos.writeUTF(pos.getTitle());
        writeNullSafely(dos, pos.getDescription());
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        LocalDate startDate = readLocalDate(dis);
        LocalDate endDate = readLocalDate(dis);
        String title = dis.readUTF();
        String description = readNullIfEmpty(dis);
        return new Organization.Position(startDate, endDate, title, description);
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeUTF(String.valueOf(ld.getMonth()));
        dos.writeInt(1);
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.valueOf(dis.readUTF());
        int dayOfMonth = dis.readInt();
        return LocalDate.of(year, month, dayOfMonth);
    }

    //https://skillbox.ru/media/base/funktsionalnye_interfeysy_i_lyambda_vyrazheniya_v_java/
    //https://metanit.com/java/tutorial/9.1.php
    private interface Writeable<T> {
        void write(T t) throws IOException;
    }

    //https://metanit.com/java/tutorial/9.2.php
    //https://coderlessons.com/tutorials/java-tekhnologii/izuchite-paket-java-util/klass-java-util-enummap
    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writeable<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private interface Readable {
        void read() throws IOException;
    }

    private void readWithException(DataInputStream dis, Readable reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}