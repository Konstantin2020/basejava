package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamStrategy implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            write(dos, resume.getUuid());
            write(dos, resume.getFullName());
            Map<ContactType, Link> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), s -> {
                ContactType ct = s.getKey();
                write(dos, ct.toString());
                writeLink(dos, s.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), s -> {
                SectionType st = s.getKey();
                write(dos, st.toString());
                AbstractSection as = s.getValue();
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeTextSection(dos, (TextSection) as);
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
            String uuid = read(dis);
            String fullName = read(dis);
            resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(read(dis)), readLink(dis)));
            readWithException(dis, () -> {
                        SectionType sectionType = SectionType.valueOf(read(dis));
                        switch (sectionType) {
                            case PERSONAL:
                            case OBJECTIVE:
                                readTextSection(dis, resume, sectionType);
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

    private void write(DataOutputStream dos, String str) throws IOException {
        if (str != null) {
            dos.writeUTF(str);
        } else {
            dos.writeUTF("");
        }
    }

    private String read(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return str.equals("") ? null : str;
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        write(dos, link.getName());
        write(dos, link.getUrl());
    }

    private Link readLink(DataInputStream dis) throws IOException {
        return new Link(read(dis), read(dis));
    }

    private void writeTextSection(DataOutputStream dos, TextSection as) throws IOException {
        write(dos, (as.getContent()));
    }

    private void readTextSection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        resume.addSection(sectionType, new TextSection(read(dis)));
    }

    private void writeListSection(DataOutputStream dos, ListSection as) throws IOException {
        List<String> list = new ArrayList<>(as.getItems());
        writeWithException(dos, list, v -> write(dos, v));
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        List<String> list = new ArrayList<>();
        readWithException(dis, () -> list.add(read(dis)));
        resume.addSection(sectionType, new ListSection(list));
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection as) throws IOException {
        List<Organization> list = new ArrayList<>(as.getOrganizations());
        writeWithException(dos, list, v -> writeOrganization(dos, v));
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        List<Organization> list = new ArrayList<>();
        readWithException(dis, () -> list.add(readOrganization(dis)));
        resume.addSection(sectionType, new OrganizationSection(list));
    }

    private void writeOrganization(DataOutputStream dos, Organization org) throws IOException {
        writeLink(dos, org.getHomePage());
        List<Organization.Position> list = new ArrayList<>(org.getPositions());
        writeWithException(dos, list, v -> writePosition(dos, v));
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Link homePage = new Link(read(dis), read(dis));
        List<Organization.Position> list = new ArrayList<>();
        readWithException(dis, () -> list.add(readPosition(dis)));
        return new Organization(homePage, list);
    }

    private void writePosition(DataOutputStream dos, Organization.Position pos) throws IOException {
        write(dos, pos.getStartDate().toString());
        write(dos, pos.getEndDate().toString());
        write(dos, pos.getTitle());
        write(dos, pos.getDescription());
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        LocalDate startDate = LocalDate.parse(Objects.requireNonNull(read(dis)));
        LocalDate endDate = LocalDate.parse(Objects.requireNonNull(read(dis)));
        String title = read(dis);
        String description = read(dis);
        return new Organization.Position(startDate, endDate, title, description);
    }

    //https://skillbox.ru/media/base/funktsionalnye_interfeysy_i_lyambda_vyrazheniya_v_java/
    //https://metanit.com/java/tutorial/9.1.php
    private interface WriteElementInterface<T> {
        void write(T t) throws IOException;
    }

    //https://metanit.com/java/tutorial/9.2.php
    //https://coderlessons.com/tutorials/java-tekhnologii/izuchite-paket-java-util/klass-java-util-enummap
    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, WriteElementInterface<T> writeElem) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writeElem.write(t);
        }
    }

    private interface ReadElementInterface {
        void read() throws IOException;
    }

    private void readWithException(DataInputStream dis, ReadElementInterface readElem) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readElem.read();
        }
    }
}