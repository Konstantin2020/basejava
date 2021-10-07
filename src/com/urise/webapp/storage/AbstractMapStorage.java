package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage<SKey> extends AbstractStorage<SKey> {

    protected Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }


    @Override
    public int size() {
        return storageMap.size();
    }

    @Override
    protected void saveToStorage(Resume resume, SKey key) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    public List<Resume> getResumesAsList() {
        return Arrays.asList(storageMap.values().toArray(new Resume[0]));
    }
}
