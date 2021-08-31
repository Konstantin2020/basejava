package com.urise.webapp.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    SortedArrayStorage storage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super();
        super.storage = this.storage;
    }
}