package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\\\projects\\\\storage");

    protected Storage storage;
    /*если указать private, то наследник AbstractArrayStorageTest не видит storage,
    и метод getOverflowWithException() для классов на основе массивов не может использовать storage
     */

    private static final String UUID_1 = "1";
    private static final String UUID_2 = "2";
    private static final String UUID_3 = "3";
    private static final String UUID_4 = "4";
    private static final String UUID_5 = "5";

    private static final String FULL_NAME_1 = "1";
    private static final String FULL_NAME_2 = "1";
    private static final String FULL_NAME_3 = "2";
    private static final String FULL_NAME_4 = "4";
    private static final String FULL_NAME_5 = "5";

    private static final Resume resume1 = ResumeTestData.fillResume(UUID_1, FULL_NAME_1);
    private static final Resume resume2 = ResumeTestData.fillResume(UUID_2, FULL_NAME_2);
    private static final Resume resume3 = ResumeTestData.fillResume(UUID_3, FULL_NAME_3);
    private static final Resume resume4 = ResumeTestData.fillResume(UUID_4, FULL_NAME_4);
    private static final Resume resume5 = ResumeTestData.fillResume(UUID_3, FULL_NAME_5);

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
        storage.update(resume5);
        assertEquals(resume5, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateWithException() throws Exception {
        storage.update(resume4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> expected = new ArrayList<>();
        expected.add(resume2);
        expected.add(resume3);
        expected.add(resume1);
        Collections.sort(expected);
        List<Resume> actual = storage.getAllSorted();
//        Arrays.sort(actual);
        assertEquals(expected, actual);
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
        storage.delete(UUID_5);
        assertSize(2);
        storage.get(UUID_5);
    }


    @Test(expected = NotExistStorageException.class)
    public void deleteWithException() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        assertGet(resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}