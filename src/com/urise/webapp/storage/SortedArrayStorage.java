package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int indexForSortSave = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + ".");
        } else if (indexForSortSave >= 0) {
            System.out.println("Резюме " + resume.getUuid() + " уже содержится в отсортированном хранилище.");
        } else {
            size++;
            System.arraycopy(storage, - indexForSortSave - 1, storage, -indexForSortSave, size - (-indexForSortSave));
            storage[-indexForSortSave - 1] = resume;
            System.out.println("Save сохраняет резюме " + resume.getUuid() + " с соблюдением сортировки.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
