package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + ".");
        } else if (getIndex(resume.getUuid()) == -1) {
            System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
            storage[size] = resume;
            size++;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        int indexForCheck = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                indexForCheck = i;
                break;
            }
        }
        if (indexForCheck != -1) {
            System.out.println("Резюме " + uuid + " в хранилище найдено!");
        } else {
            System.out.println("Резюме " + uuid + " в хранилище не найдено!");
        }
        return indexForCheck;
    }
}
