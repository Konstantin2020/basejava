package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToArray(Resume resume, int indexForSave) {
        indexForSave = -indexForSave;
        System.arraycopy(storage, indexForSave - 1, storage, indexForSave, size - indexForSave);
        storage[indexForSave - 1] = resume;
    }
}
