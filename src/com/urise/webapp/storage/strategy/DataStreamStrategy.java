package com.urise.webapp.storage.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStreamStrategy implements SerializeStrategy {
    Resume resume;

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            write(dos, resume.getUuid());
            write(dos, resume.getFullName());
            Map<ContactType, Link> contacts = resume.getContacts();
            writeInt(dos, contacts.size());
            contacts.forEach((key, value) -> {
                write(dos, key.toString());
                writeLink(dos, value);
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeInt(dos, sections.size());
            sections.forEach((key, as) -> {
                write(dos, key.toString());
                Class<? extends AbstractSection> abstractSection = as.getClass();
                write(dos, String.valueOf(abstractSection));
                if (abstractSection.equals(TextSection.class)) {
                    writeTextSection(dos, (TextSection) as);
                } else if (abstractSection.equals(ListSection.class)) {
                    writeListSection(dos, (ListSection) as);
                } else if (abstractSection.equals(OrganizationSection.class)) {
                    writeOrganizationSection(dos, (OrganizationSection) as);
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = read(dis);
            String fullName = read(dis);
            resume = new Resume(uuid, fullName);
            int sizeContactType = readInt(dis);
            for (int i = 0; i < sizeContactType; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), readLink(dis));
            }
            int sizeSections = readInt(dis);
            for (int i = 0; i < sizeSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                String abstractSectionName = dis.readUTF();
                if (abstractSectionName.equals(TextSection.class.toString())) {
                    readTextSection(dis, sectionType);
                } else if (abstractSectionName.equals(ListSection.class.toString())) {
                    readListSection(dis, sectionType);
                } else if (abstractSectionName.equals(OrganizationSection.class.toString())) {
                    readOrganizationSection(dis, sectionType);
                }
            }
            return resume;
        }
    }

    private void write(DataOutputStream dos, String str) {
        try {
            if (str != null) {
                dos.writeUTF(str);
            } else {
                dos.writeUTF("");
            }
        } catch (IOException e) {
            throw new StorageException("Data stream write error", e);
        }
    }

    private String read(DataInputStream dis) {
        try {
            String str = dis.readUTF();
            return str.equals("") ? null : str;
        } catch (IOException e) {
            throw new StorageException("Data stream read error", e);
        }
    }

    private void writeInt(DataOutputStream dos, Integer size) {
        try {
            dos.writeInt(size);
        } catch (IOException e) {
            throw new StorageException("Data stream write error for int", e);
        }
    }

    private int readInt(DataInputStream dis) {
        try {
            return dis.readInt();
        } catch (IOException e) {
            throw new StorageException("Data stream read error for int", e);
        }
    }

    private void writeLink(DataOutputStream dos, Link link) {
        write(dos, link.getName());
        write(dos, link.getUrl());
    }

    private Link readLink(DataInputStream dis) {
        return new Link(read(dis), read(dis));
    }

    private void writeTextSection(DataOutputStream dos, TextSection as) {
        write(dos, (as.getContent()));
    }

    private void readTextSection(DataInputStream dis, SectionType sectionType) {
        resume.addSection(sectionType, new TextSection(read(dis)));
    }

    private void writeListSection(DataOutputStream dos, ListSection as) {
        List<String> list = new ArrayList<>(as.getItems());
        writeInt(dos, list.size());
        list.forEach(str ->
                write(dos, str));
    }

    private void readListSection(DataInputStream dis, SectionType sectionType) {
        List<String> list = new ArrayList<>();
        int size = readInt(dis);
        for (int i = 0; i < size; i++) {
            list.add(read(dis));
        }
        resume.addSection(sectionType, new ListSection(list));
    }


    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection as) {
        List<Organization> list = new ArrayList<>(as.getOrganizations());
        writeInt(dos, list.size());
        list.forEach(organization ->
                writeOrganization(dos, organization));
    }

    private void readOrganizationSection(DataInputStream dis, SectionType sectionType) {
        List<Organization> list = new ArrayList<>();
        int size = readInt(dis);
        for (int i = 0; i < size; i++) {
            list.add(readOrganization(dis));
        }
        resume.addSection(sectionType, new OrganizationSection(list));
    }

    private void writeOrganization(DataOutputStream dos, Organization org) {
        writeLink(dos, org.getHomePage());
        List<Organization.Position> list = new ArrayList<>(org.getPositions());
        writeInt(dos, list.size());
        list.forEach(position ->
                writePosition(dos, position));

    }

    private Organization readOrganization(DataInputStream dis) {
        Link homePage = readLink(dis);
        List<Organization.Position> list = new ArrayList<>();
        int size = readInt(dis);
        for (int i = 0; i < size; i++) {
            list.add(readPosition(dis));
        }
        return new Organization(homePage, list);
    }

    private void writePosition(DataOutputStream dos, Organization.Position pos) {
        write(dos, pos.getStartDate().toString());
        write(dos, pos.getEndDate().toString());
        write(dos, pos.getTitle());
        write(dos, pos.getDescription());
    }

    private Organization.Position readPosition(DataInputStream dis) {
        LocalDate startDate = LocalDate.parse(Objects.requireNonNull(read(dis)));
        LocalDate endDate = LocalDate.parse(Objects.requireNonNull(read(dis)));
        String title = read(dis);
        String description = read(dis);
        return new Organization.Position(startDate, endDate, title, description);
    }
}
