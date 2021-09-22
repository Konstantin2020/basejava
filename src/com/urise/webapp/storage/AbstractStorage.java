package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10; //_000;

    protected int size = 0;

    @Override
    public final void save(Resume resume) {
        int indexForSave = getIndex(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (indexForSave >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        size++;
        saveToArray(resume, indexForSave);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final void update(Resume resume) {
        updateToArray(resume, checkIndexForResume(resume));
    }

    @Override
    public final void delete(String uuid) {
        deleteFromArray(checkIndexForUuid(uuid));
    }

    public int checkIndexForResume(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return index;
    }

    public int checkIndexForUuid(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract int getIndex(String uuid);

    protected abstract void saveToArray(Resume resume, int indexForSave);

    protected abstract void updateToArray(Resume resume, int indexForUpdate);

    protected abstract void deleteFromArray(int indexForDelete);
}
