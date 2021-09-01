package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    public Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    public AbstractArrayStorageTest() {
        this.storage = null;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resumeForUpdate = new Resume(UUID_4);
        storage.save(resumeForUpdate);
        storage.update(resumeForUpdate);
        assertEquals(resumeForUpdate, storage.get(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWithException() throws Exception {
        storage.update(resume4);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] actual = storage.getAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void save() throws Exception {
        storage.save(resume4);
        assertEquals(resume4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveWithException() throws Exception {
        storage.save(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        if (storage.size() == 2) storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteWithException() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Resume resumeExpected = new Resume(UUID_1);
        Resume resumeActual = storage.get(UUID_1);
        assertEquals(resumeExpected, resumeActual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test(expected = StorageException.class)
    public void getOverflowWithException() throws Exception {
        for (int i = storage.size() + 1; i <= STORAGE_LIMIT; i++) {
            storage.save(new Resume());
            if (storage.size() > STORAGE_LIMIT) {
                fail("Переполнение произошло раньше времени");
            }
            if (storage.size() == STORAGE_LIMIT) {
                System.out.println("Заполненность хранилища: " + storage.size() + " из " + STORAGE_LIMIT);
                System.out.println("Попытка реализовать переполнение и вызвать исключение...");
                storage.save(new Resume());
            }
        }
    }
}