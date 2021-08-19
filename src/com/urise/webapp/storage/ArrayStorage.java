package com.urise.webapp.storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
