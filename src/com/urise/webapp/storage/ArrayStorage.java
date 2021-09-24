package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        int indexForCheck = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                indexForCheck = i;
                break;
            }
        }
        return indexForCheck;
    }

    @Override
    protected void saveToStorage(Resume resume, int indexForSave) {
        storage[size - 1] = resume;
    }
}
