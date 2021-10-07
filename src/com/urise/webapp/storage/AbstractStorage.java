package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SKey> implements Storage {

    @Override
    public void save(Resume resume) {
        SKey searchKey = getSearchKey(resume.getUuid());
        if (isExistInStorage(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveToStorage(resume, searchKey);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final Resume get(String uuid) {
        return getFromStorage(checkSearchKey(uuid));
    }

    @Override
    public final void update(Resume resume) {
        updateToStorage(resume, checkSearchKey(resume.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        deleteFromStorage(checkSearchKey(uuid));
    }

    public static final Comparator<Resume> GENERAL_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final SKey checkSearchKey(String uuid) {
        SKey searchKey = getSearchKey(uuid);
        if (!isExistInStorage(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        List<Resume> allResume = getResumesAsList();
        allResume.sort(GENERAL_COMPARATOR);
        return allResume;
    }

    protected abstract SKey getSearchKey(String uuid);

    protected abstract void saveToStorage(Resume resume, SKey searchKey);

    protected abstract Resume getFromStorage(SKey searchKey);

    protected abstract void updateToStorage(Resume resume, SKey searchKey);

    protected abstract void deleteFromStorage(SKey searchKey);

    protected abstract boolean isExistInStorage(SKey searchKey);

    public abstract List<Resume> getResumesAsList();
}
