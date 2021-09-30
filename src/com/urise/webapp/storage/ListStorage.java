package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List storageList = new ArrayList<Resume>();

    @Override
    protected Integer getIndex(String uuid) {
        return storageList.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveToStorage(Resume resume, Integer index) {
        storageList.add(resume);
    }

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    protected void updateToStorage(Resume resume, Integer index) {
        System.out.println("Update обновляет резюме " + storageList.get(index) + ".");
        storageList.set(index, resume);
    }

    @Override
    protected final Resume getFromStorage(Integer index) {
        return (Resume) storageList.get(index);
    }

    @Override
    protected void deleteFromStorage(Integer index) {
        storageList.remove((int) index);
    }

    @Override
    protected final boolean isExistInStorage(Integer searchKey) {
        if (searchKey > -1) {
            return true;
        }
        return false;
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
