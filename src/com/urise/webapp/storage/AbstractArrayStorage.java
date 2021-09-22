package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public final int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateToArray(Resume resume, int indexForUpdate) {
        System.out.println("Update обновляет резюме " + storage[indexForUpdate].getUuid() + ".");
        storage[indexForUpdate] = resume;
    }


    public final void deleteFromArray(int indexForDelete) {
        System.out.println("Резюме " + storage[indexForDelete].getUuid() + " удалено!");
        storage[indexForDelete] = null;
        if (size - (indexForDelete + 1) >= 0)
            System.arraycopy(storage, indexForDelete + 1, storage, indexForDelete, size - (indexForDelete + 1));
        storage[size - 1] = null;
        size--;
    }

    @Override
    public final Resume get(String uuid) {
        return storage[checkIndexForUuid(uuid)];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
