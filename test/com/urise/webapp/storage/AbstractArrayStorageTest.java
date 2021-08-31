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

    public AbstractArrayStorageTest() {
        this.storage = null;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        String strForUpdate = "uuid6";
        Resume resumeForUpdate = new Resume(strForUpdate);
        storage.save(resumeForUpdate);
        storage.update(resumeForUpdate);
        assertEquals(resumeForUpdate, storage.get(strForUpdate));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWithException() throws Exception {
        Resume resumeForUpdate = new Resume("uuid6");
        storage.update(resumeForUpdate);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = storage.getAll();
        Resume[] actual = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void save() throws Exception {
        int result = -1;
        String strForSave = "uuid6";
        storage.save(new Resume(strForSave));
        if (storage.get(strForSave) != null) {
            result = 4;
        }
        assertEquals(result, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveWithException() throws Exception {
        storage.save(new Resume("uuid3"));
    }

    @Test
    public void delete() throws Exception {
        Resume resume = storage.get(UUID_2);
        storage.delete(resume.getUuid());
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteWithException() throws Exception {
        Resume resume = storage.get("uuid4");
        storage.delete(resume.getUuid());
    }

    @Test
    public void get() throws Exception {
        boolean isGet = (storage.get(UUID_1) != null);
        assertEquals(true, isGet);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void getOverflowWithException() throws Exception {
        for (int i = storage.size() + 1; i <= (STORAGE_LIMIT); i++) {
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