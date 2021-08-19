package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        int indexForSave = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + ".");
        } else if (indexForSave >= 0) {
            System.out.println("Резюме " + resume.getUuid() + " уже содержится в хранилище.");
        } else {
            finalSave(resume, indexForSave);
            System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
        }
    }

    @Override
    public final void update(Resume resume) {
        int indexForUpdate = getIndex(resume.getUuid());
        if (indexForUpdate >= 0) {
            System.out.println("Update обновляет резюме " + storage[indexForUpdate].getUuid() + ".");
            storage[indexForUpdate] = resume;
        }
    }

    @Override
    public final void delete(String uuid) {
        int indexForDelete = getIndex(uuid);
        if (indexForDelete >= 0) {
            System.out.println("Резюме " + storage[indexForDelete].getUuid() + " удалено!");
            storage[indexForDelete] = null;
            if (size - (indexForDelete + 1) >= 0)
                System.arraycopy(storage, indexForDelete + 1, storage, indexForDelete, size - (indexForDelete + 1));
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void finalSave(Resume resume, int indexForFinalSave);
}
