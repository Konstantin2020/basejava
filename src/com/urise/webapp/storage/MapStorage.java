package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storageMap = new LinkedHashMap<>();

    @Override
    protected int getIndex(String uuid) {
        int isContainsInMap = -1;
        if (storageMap.containsKey(uuid)) {
            isContainsInMap = 1;
        }
        return isContainsInMap;
    }


    @Override
    protected void saveToStorage(Resume resume, int index) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, int index) {
        System.out.println("Update обновляет резюме " + storageMap.get(resume.getUuid()) + ".");
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume getFromStorage(String uuid, int index) {
        return storageMap.get(uuid);
    }

    @Override
    protected void deleteFromStorage(String uuid, int index) {
        storageMap.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumesToArray = new Resume[storageMap.size()];
        int i = 0;
        for (java.util.Map.Entry<String, Resume> entry : storageMap.entrySet()) {
            resumesToArray[i] = entry.getValue();
            i++;
        }
        return resumesToArray;
    }

    @Override
    public int size() {
        return storageMap.size();
    }

}
