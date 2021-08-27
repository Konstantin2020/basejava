package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    SortedArrayStorage storage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super();
        super.storage = this.storage;
    }

    @Test
    public void getIndex() throws Exception {
        int forGet = storage.getIndex("uuid1");
        Assert.assertEquals(0, forGet);
    }

    @Test(expected = NullPointerException.class)
    public void getIndexWithException() throws Exception {
        storage = null;
        storage.getIndex("uuid8");
    }

    @Test
    public void saveToArray() throws Exception {
        storage.save(new Resume("uuid4"));
        int indexFromArray = storage.getIndex("uuid4");
        Assert.assertEquals(3, indexFromArray);
    }

    @Test(expected = ExistStorageException.class)
    public void saveToArrayWithException() throws Exception {
        storage.save(new Resume("uuid3"));
        int indexFromArray = storage.getIndex("uuid3");
    }
}