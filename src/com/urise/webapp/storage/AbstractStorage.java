package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveToStorage(resume, index);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final Resume get(String uuid) {
        return getFromStorage(uuid, checkIndex(uuid));
    }

    @Override
    public final void update(Resume resume) {
        updateToStorage(resume, checkIndex(resume.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        deleteFromStorage(uuid, checkIndex(uuid));
    }

    public final int checkIndex(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveToStorage(Resume resume, int index);

    protected abstract Resume getFromStorage(String uuid, int index);

    protected abstract void updateToStorage(Resume resume, int index);

    protected abstract void deleteFromStorage(String uuid, int index);
}
