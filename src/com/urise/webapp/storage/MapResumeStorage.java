package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected Resume getFromStorage(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void updateToStorage(Resume resume, Resume searchKey) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(Resume searchKey) {
        storageMap.remove(searchKey.getUuid());
    }

    @Override
    protected final boolean isExistInStorage(Resume resume) {
        return storageMap.containsValue(resume);
    }

}
