package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List storageList = new ArrayList<Resume>();

    @Override
    protected int getIndex(String uuid) {
        return storageList.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveToStorage(Resume resume, int indexForSave) {
        storageList.add(resume);
    }

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, int indexForUpdate) {
        System.out.println("Update обновляет резюме " + storageList.get(indexForUpdate) + ".");
        storageList.set(indexForUpdate, resume);
    }

    @Override
    protected final Resume getFromStorage(String uuid) {
        return (Resume) storageList.get(checkIndex(uuid));
    }

    @Override
    protected void deleteFromStorage(int indexFordelete) {
        storageList.remove(indexFordelete);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumesToArray = new Resume[storageList.size()];
        resumesToArray = (Resume[]) storageList.toArray(resumesToArray);
        return resumesToArray;
    }

    @Override
    public int size() {
        return storageList.size();
    }

}
