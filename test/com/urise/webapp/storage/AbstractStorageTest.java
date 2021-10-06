package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    public Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private static final String FULL_NAME_1 = "FN1";
    private static final String FULL_NAME_2 = "FN2";
    private static final String FULL_NAME_3 = "FN3";
    private static final String FULL_NAME_4 = "FN4";
    private static final String FULL_NAME_5 = "FN5";

    private static final Resume resume1;
    private static final Resume resume21;
    private static final Resume resume3;
    private static final Resume resume4;
    private static final Resume resume5;


    static {
        resume1 = new Resume(UUID_1, FULL_NAME_1);
        resume21 = new Resume(UUID_5, FULL_NAME_3);
        resume3 = new Resume(UUID_3, FULL_NAME_3);
        resume4 = new Resume(UUID_4, FULL_NAME_4);
        resume5 = new Resume(UUID_3, FULL_NAME_5);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume21);
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
        List<Resume> expected = Arrays.asList( resume1, resume3, resume21);
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
        assertGet(resume21);
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