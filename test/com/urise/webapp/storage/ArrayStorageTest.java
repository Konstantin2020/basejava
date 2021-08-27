package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorage storage = new ArrayStorage();

    public ArrayStorageTest() {
        super();
        super.storage = storage;
    }

    @Test
    public void getIndex() throws Exception {
        Resume resume = storage.get("uuid1");
        int forGet = storage.getIndex(resume.getUuid());
        Assert.assertEquals(0, forGet);
    }

    @Test(expected = NullPointerException.class)
    public void getIndexWithException() throws Exception {
        storage = null;
        storage.getIndex("uuid8");
    }

    @Test
    public void saveToArray() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        int indexFromArray = storage.getIndex(resume.getUuid());
        Assert.assertEquals(3, indexFromArray);
    }

    @Test(expected = ExistStorageException.class)
    public void saveToArrayWithException() throws Exception {
        Resume resume = new Resume("uuid3");
        storage.save(resume);
    }

}
