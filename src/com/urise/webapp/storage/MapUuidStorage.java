package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected final Resume getFromStorage(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void updateToStorage(Resume resume, String searchKey) {
        storageMap.put(searchKey, resume);
    }

    @Override
    protected void deleteFromStorage(String searchKey) {
        storageMap.remove(searchKey);
    }

    @Override
    protected final boolean isExistInStorage(String uuid) {
        return storageMap.containsKey(uuid);
    }
}
