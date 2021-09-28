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
    protected void saveToStorage(Resume resume, int index) {
        index = -index;
        System.arraycopy(storage, index - 1, storage, index, size - index);
        storage[index - 1] = resume;

    }
}
