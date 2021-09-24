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
        int indexForSave = getIndex(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (indexForSave >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        size++;
        saveToStorage(resume, indexForSave);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }


    @Override
    protected final void updateToStorage(Resume resume, int indexForUpdate) {
        System.out.println("Update обновляет резюме " + storage[indexForUpdate].getUuid() + ".");
        storage[indexForUpdate] = resume;
    }


    protected final void deleteFromStorage(int indexForDelete) {
        System.out.println("Резюме " + storage[indexForDelete].getUuid() + " удалено!");
        storage[indexForDelete] = null;
        if (size - (indexForDelete + 1) >= 0)
            System.arraycopy(storage, indexForDelete + 1, storage, indexForDelete, size - (indexForDelete + 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final Resume getFromStorage(String uuid) {
        return storage[checkIndexForUuid(uuid)];
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
