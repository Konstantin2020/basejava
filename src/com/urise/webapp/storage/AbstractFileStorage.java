package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable directory");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void saveToStorage(Resume resume, File searchKey) {
        try {
            searchKey.createNewFile();
            doWrite(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File searchKey);

    @Override
    protected Resume getFromStorage(File searchKey) {
        return doRead(searchKey);
    }

    protected abstract Resume doRead(File searchKey);

    @Override
    protected void updateToStorage(Resume resume, File searchKey) {
        doWrite(resume, searchKey);
    }

    @Override
    protected void deleteFromStorage(File searchKey) {
        searchKey.delete();
    }

    @Override
    protected boolean isExistInStorage(File searchKey) {
        return searchKey.exists();
    }

    @Override
    public List<Resume> getResumesAsList() {
        File[] fileArray = directory.listFiles();
        List<Resume> resumes = new ArrayList<>();
        for (File zdf : fileArray) {
            resumes.add(doRead(zdf));
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] fileArray = directory.listFiles();
        for (File clr : fileArray) {
            clr.delete();
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}
