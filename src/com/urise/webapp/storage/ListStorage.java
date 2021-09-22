package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> collection = new ArrayList<>();

    @Override
    int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        int indexForCheck = collection.indexOf(resume);
        return indexForCheck;
    }


    @Override
    protected void saveToArray(Resume resume, int indexForSave) {
        collection.add(resume);
    }

    @Override
    public void clear() {
        collection.removeAll(collection);
    }

    @Override
    public void updateToArray(Resume resume, int indexForUpdate) {
        System.out.println("Update обновляет резюме " + collection.get(indexForUpdate) + ".");
        collection.set(indexForUpdate, resume);
    }

    @Override
    public final Resume get(String uuid) {
        return collection.get(checkIndexForUuid(uuid));
    }

    @Override
    public void deleteFromArray(int indexFordelete) {
        collection.remove(indexFordelete);
        size--;
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumesToArray = new Resume[collection.size()];
        resumesToArray = collection.toArray(resumesToArray);
        return resumesToArray;
    }

    @Override
    public int size() {
        return collection.size();
    }

}
