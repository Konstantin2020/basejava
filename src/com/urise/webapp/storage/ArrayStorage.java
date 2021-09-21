package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    int getIndex(String uuid) {
        int indexForCheck = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == new Resume(uuid).getUuid()) {
                indexForCheck = i;
                break;
            }
        }
        return indexForCheck;
    }

    @Override
    protected void saveToArray(Resume resume, int indexForSave) {
        size++;
        storage[size - 1] = resume;

    }
}
