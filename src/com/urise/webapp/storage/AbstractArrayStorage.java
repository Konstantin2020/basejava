package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10; //_000;

    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        size++;
        saveToStorage(resume, index);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }


    @Override
    protected final void updateToStorage(Resume resume, int index) {
        System.out.println("Update обновляет резюме " + storage[index].getUuid() + ".");
        storage[index] = resume;
    }


    protected final void deleteFromStorage(String uuid, int index) {
        System.out.println("Резюме " + storage[index].getUuid() + " удалено!");
        storage[index] = null;
        if (size - (index + 1) >= 0)
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final Resume getFromStorage(String uuid, int index) {
        return storage[index];
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
