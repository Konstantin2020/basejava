package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> storageMap = new HashMap<>();

    @Override
    protected Resume getIndex(String uuid) {
        return storageMap.get(uuid);
    }

    protected final boolean isExistInStorage(Resume resume) {
        return resume != null;
    }


    @Override
    protected void saveToStorage(Resume resume, Resume searchKey) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, Resume searchKey) {
        System.out.println("Update обновляет резюме " + storageMap.get(resume.getUuid()) + ".");
        storageMap.put(searchKey.getUuid(), resume);
    }

    @Override
    protected final Resume getFromStorage(Resume searchKey) {
        return storageMap.get(searchKey.getUuid());
    }

    @Override
    protected void deleteFromStorage(Resume searchKey) {
        storageMap.remove(searchKey.getUuid());
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumesToArray = storageMap.values().toArray(new Resume[storageMap.size()]);
        return resumesToArray;
    }

    @Override
    public int size() {
        return storageMap.size();
    }

}
