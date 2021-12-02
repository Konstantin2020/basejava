package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializeStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializeStrategy strategy;

    protected PathStorage(String dir, SerializeStrategy strategy) {
        directory = Paths.get(dir);
        this.strategy = strategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    private Stream<Path> createStreamPath() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public void clear() {
        createStreamPath().forEach(this::deleteFromStorage);
    }

    @Override
    public int size() {
        return (int) createStreamPath().count();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateToStorage(Resume resume, Path searchKey) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Path write error " + searchKey.toString(), resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExistInStorage(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected void saveToStorage(Resume resume, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + searchKey.toString(), resume.getUuid(), e);
        }
        updateToStorage(resume, searchKey);
    }

    @Override
    protected Resume getFromStorage(Path searchKey) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Path read error", searchKey.toString(), e);
        }
    }

    @Override
    protected void deleteFromStorage(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error", searchKey.toString(), e);
        }
    }

    @Override
    public List<Resume> getResumesAsList() {
        return createStreamPath().map(path -> getFromStorage(path)).collect(Collectors.toList());
    }
}
