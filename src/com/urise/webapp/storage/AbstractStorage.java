package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SKey> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SKey searchKey = getSearchKey(resume.getUuid());
        if (isExistInStorage(searchKey)) {
            LOG.warning("Resume " + resume.getUuid() + " already exist");
            throw new ExistStorageException(resume.getUuid());
        }
        saveToStorage(resume, searchKey);
        System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getFromStorage(checkSearchKey(uuid));
    }

    @Override
    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        updateToStorage(resume, checkSearchKey(resume.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteFromStorage(checkSearchKey(uuid));
    }

    //public static final Comparator<Resume> GENERAL_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final SKey checkSearchKey(String uuid) {
        SKey searchKey = getSearchKey(uuid);
        if (!isExistInStorage(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        List<Resume> allResume = getResumesAsList();
        Collections.sort(allResume);
        // allResume.sort(GENERAL_COMPARATOR);
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
