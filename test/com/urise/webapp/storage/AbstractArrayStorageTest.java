package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractArrayStorageTest {

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
        int sizeArray = -1;
        sizeArray = storage.size();
        Assert.assertEquals(3, sizeArray);
    }

    @Test(expected = NullPointerException.class)
    public void sizeWithException() throws Exception {
        storage = null;
        storage.size();
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NullPointerException.class)
    public void clearWithException() throws Exception {
        storage = null;
        storage.clear();
    }

    @Test
    public void update() throws Exception {
        boolean getForUpdate = false;
        String strForUpdate = "uuid2";
        Resume resumeForUpdate = storage.get(strForUpdate);
        if (resumeForUpdate != null) {
            Resume resume = new Resume(strForUpdate);
            storage.update(resume);
            getForUpdate = true;
            Assert.assertEquals(true, getForUpdate);
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWithException() throws Exception {
        String strForUpdate = "uuid6";
        storage.get(strForUpdate);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = storage.getAll();
        Resume[] actual = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getAllWithException() throws Exception {
        storage = null;
        Resume[] expected = storage.getAll();
    }


    @Test
    public void save() throws Exception {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveWithException() throws Exception {
        storage.save(new Resume("uuid4"));
        storage.save(new Resume("uuid4"));
    }

    @Test
    public void delete() throws Exception {
        Resume resume = storage.get(UUID_2);
        storage.delete(resume.getUuid());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteWithException() throws Exception {
        Resume resume = storage.get("uuid4");
        storage.delete(resume.getUuid());
    }


    @Test
    public void get() throws Exception {
        boolean isGet = (storage.get(UUID_1) != null);
        Assert.assertEquals(true, isGet);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getOverflowWithFail() throws Exception {
        for (int i = storage.size() + 1; i <= (STORAGE_LIMIT); i++) {
            storage.save(new Resume());
            if (storage.size() + 1 > STORAGE_LIMIT) {
                Assert.fail("Переполнение произошло раньше времени");
            }
        }
        System.out.println("Заполненность хранилища: " + storage.size() + " из " + STORAGE_LIMIT);
        storage.save(new Resume());
    }

    @Test(expected = StorageException.class)
    public void getOverflowWithException() throws Exception {
        for (int i = storage.size() + 1; i <= (STORAGE_LIMIT); i++) {
            storage.save(new Resume());
            if (storage.size() > STORAGE_LIMIT) {
                Assert.fail("Переполнение произошло раньше времени");
            }
            System.out.println("Заполненность хранилища: " + storage.size() + " из " + STORAGE_LIMIT);
            storage.save(new Resume());
        }
    }
}