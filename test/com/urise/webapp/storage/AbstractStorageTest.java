package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    public Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume resume1;
    private static final Resume resume2;
    private static final Resume resume3;
    private static final Resume resume4;

    static {
        resume1 = new Resume(UUID_1);
        resume2 = new Resume(UUID_2);
        resume3 = new Resume(UUID_3);
        resume4 = new Resume(UUID_4);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
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
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        storage.update(resume2);
        assertTrue(resume2 == storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWithException() throws Exception {
        storage.update(resume4);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = {resume1, resume2, resume3};
        Resume[] actual = storage.getAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void save() throws Exception {
        storage.save(resume4);
        assertSize(4);
        assertGet(resume4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveWithException() throws Exception {
        storage.save(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }


    @Test(expected = NotExistStorageException.class)
    public void deleteWithException() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        assertGet(resume1);
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

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}