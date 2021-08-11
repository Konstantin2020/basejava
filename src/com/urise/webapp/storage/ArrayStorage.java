package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int indexForUpdate = checkInStorage(resume.getUuid());
        if (indexForUpdate != -1) {
            System.out.println("Update обновляет резюме " + storage[indexForUpdate].getUuid() + ".");
            storage[indexForUpdate] = resume;
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + ".");
        } else if (checkInStorage(resume.getUuid()) == -1) {
            System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int indexForGet = checkInStorage(uuid);
        Resume resumeForGet = null;
        if (indexForGet != -1) {
            resumeForGet = storage[indexForGet];
        }
        return resumeForGet;
    }

    public void delete(String uuid) {
        int indexForDelete = checkInStorage(uuid);
        if (indexForDelete != -1) {
            System.out.println("Резюме " + storage[indexForDelete].getUuid() + " удалено!");
            storage[indexForDelete] = null;
            if (size - (indexForDelete + 1) >= 0)
                System.arraycopy(storage, indexForDelete + 1, storage, indexForDelete + 1 - 1, size - (indexForDelete + 1));
            storage[size - 1] = null;
            size--;
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        System.arraycopy(storage, 0, allResume, 0, allResume.length);
        return allResume;
    }

    public int size() {
        return size;
    }

    public int checkInStorage(String uuid) {
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

