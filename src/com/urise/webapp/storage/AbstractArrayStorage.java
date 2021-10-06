package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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
    public final void saveToStorage(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        size++;
        saveToArray(resume, index);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    protected abstract void saveToArray(Resume resume, Integer index);


    @Override
    protected final void updateToStorage(Resume resume, Integer index) {
        System.out.println("Update обновляет резюме " + storage[index].getUuid() + ".");
        storage[index] = resume;
    }


    protected final void deleteFromStorage(Integer index) {
        System.out.println("Резюме " + storage[index].getUuid() + " удалено!");
        storage[index] = null;
        if (size - (index + 1) >= 0)
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final boolean isExistInStorage(Integer index) {
        return index > -1;
    }

    @Override
    protected final Resume getFromStorage(Integer index) {
        return storage[index];
    }

    @Override
    public final List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

}
