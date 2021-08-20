package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToArray(Resume resume, int indexForSaveToArray) {
        indexForSaveToArray = -indexForSaveToArray;
        System.arraycopy(storage, indexForSaveToArray - 1, storage, indexForSaveToArray, size - (indexForSaveToArray));
        storage[indexForSaveToArray - 1] = resume;
    }
}
