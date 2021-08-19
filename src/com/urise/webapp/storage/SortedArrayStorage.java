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
    protected void finalSave(Resume resume, int indexForFinalSave) {
        size++;
        System.arraycopy(storage, -indexForFinalSave - 1, storage, -indexForFinalSave, size - (-indexForFinalSave));
        storage[-indexForFinalSave - 1] = resume;
    }
}
