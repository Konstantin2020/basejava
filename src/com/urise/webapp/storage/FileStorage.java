package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializeStrategy;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializeStrategy strategy;

    protected FileStorage(File directory, SerializeStrategy strategy) {
        this.strategy = strategy;
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
    public void clear() {
        for (File file : createFilesArray()) {
            deleteFromStorage(file);
        }
    }

    @Override
    public int size() {
        return createFilesArray().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateToStorage(Resume resume, File searchKey) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExistInStorage(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void saveToStorage(Resume resume, File searchKey) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + searchKey.getAbsolutePath(), searchKey.getName(), e);
        }
        updateToStorage(resume, searchKey);
    }

    @Override
    protected Resume getFromStorage(File searchKey) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("File read error", searchKey.getName(), e);
        }
    }

    @Override
    protected void deleteFromStorage(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("File delete error", searchKey.getName());
        }
    }

    @Override
    public List<Resume> getResumesAsList() {
        return Arrays.stream(createFilesArray())
                .map(this::getFromStorage)
                .collect(Collectors.toList());
    }

    private File[] createFilesArray() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files;
    }
}
