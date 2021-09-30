package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<SKey> implements Storage {

    @Override
    public void save(Resume resume) {
        SKey searchKey = getIndex(resume.getUuid());
        if (isExistInStorage(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveToStorage(resume, searchKey);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final Resume get(String uuid) {
        return getFromStorage(checkIndex(uuid));
    }

    @Override
    public final void update(Resume resume) {
        updateToStorage(resume, checkIndex(resume.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        deleteFromStorage(checkIndex(uuid));
    }

    public final SKey checkIndex(String uuid) {
        SKey searchKey = getIndex(uuid);
        if (!isExistInStorage(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SKey getIndex(String uuid);

    protected abstract void saveToStorage(Resume resume, SKey searchKey);

    protected abstract Resume getFromStorage(SKey searchKey);

    protected abstract void updateToStorage(Resume resume, SKey searchKey);

    protected abstract void deleteFromStorage(SKey searchKey);

    protected abstract boolean isExistInStorage(SKey searchKey);
}
