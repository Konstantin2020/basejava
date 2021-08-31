package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10; //_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
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
        saveToArray(resume, indexForSave);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final void update(Resume resume) {
        int indexForUpdate = getIndex(resume.getUuid());
        if (indexForUpdate < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        System.out.println("Update обновляет резюме " + storage[indexForUpdate].getUuid() + ".");
        storage[indexForUpdate] = resume;
    }

    @Override
    public final void delete(String uuid) {
        int indexForDelete = getIndex(uuid);
        if (indexForDelete < 0) {
            throw new NotExistStorageException(uuid);
        }
        System.out.println("Резюме " + storage[indexForDelete].getUuid() + " удалено!");
        storage[indexForDelete] = null;
        if (size - (indexForDelete + 1) >= 0)
            System.arraycopy(storage, indexForDelete + 1, storage, indexForDelete, size - (indexForDelete + 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    abstract int getIndex(String uuid);

    protected abstract void saveToArray(Resume resume, int indexForSave);
}
