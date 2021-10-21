package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storageList = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        int indexForCheck = -1;
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid() == uuid) {
                indexForCheck = i;
                break;
            }
        }
        return indexForCheck;
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
        return storageList.get(index);
    }

    @Override
    protected void deleteFromStorage(Integer index) {
        storageList.remove((int) index);
    }

    @Override
    protected final boolean isExistInStorage(Integer index) {
        return index > -1;
    }

    @Override
    public List<Resume> getResumesAsList() {
        return new ArrayList<>(storageList);
    }

    @Override
    public int size() {
        return storageList.size();
    }

}
