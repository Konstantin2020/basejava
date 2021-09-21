package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public abstract class AbstractStorage implements Storage {

    ArrayList<Resume> collection = new ArrayList<>();

    @Override
    public abstract void clear();

    @Override
    public abstract void update(Resume resume);

    @Override
    public void save(Resume resume) {
        int indexForSave = getIndex(resume.getUuid());
        if (indexForSave >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveToArray(resume, indexForSave);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    abstract int getIndex(String uuid);

    protected abstract void saveToArray(Resume resume, int indexForSave);
}
