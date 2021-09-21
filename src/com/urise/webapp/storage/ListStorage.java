package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    @Override
    int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        int indexForCheck = collection.indexOf(resume);
        return indexForCheck;
    }

    @Override
    protected void saveToArray(Resume resume, int indexForSave) {
        collection.add(resume);
    }

    @Override
    public void clear() {
        collection.removeAll(collection);
    }

    @Override
    public void update(Resume resume) {
        int indexForUpdate = getIndex(resume.getUuid());
        if (indexForUpdate < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        System.out.println("Update обновляет резюме " + collection.get(indexForUpdate) + ".");
        collection.set(indexForUpdate, resume);
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return collection.get(index);
    }

    @Override
    public void delete(String uuid) {
        int indexForDelete = getIndex(uuid);
        if (indexForDelete < 0) {
            throw new NotExistStorageException(uuid);
        }
        collection.remove(getIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumesToArray = new Resume[collection.size()];
        resumesToArray = collection.toArray(resumesToArray);
        return resumesToArray;
    }

    @Override
    public int size() {
        return collection.size();
    }

}
