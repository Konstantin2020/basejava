package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;
import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void getOverflowWithException() throws Exception {
        try {
            for (int i = storage.size() + 1; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.valueOf("Name" + i)));
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
        System.out.println("Заполненность хранилища: " + storage.size() + " из " + STORAGE_LIMIT);
        System.out.println("Попытка реализовать переполнение и вызвать исключение...");
        storage.save(new Resume("Overflow"));
    }

}