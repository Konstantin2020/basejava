/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        Resume resumeForGet = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                resumeForGet = storage[i];
                break;
            }
        }
        return resumeForGet;
    }

    void delete(String uuid) {
        int countForMove = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                countForMove = i;
                break;
            }
        }
        if (countForMove != -1) {
            for (int j = countForMove + 1; j < size; j++) {
                storage[j - 1] = storage[j];
            }
            storage[size - 1] = null;
            size--;
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int j = 0; j < allResume.length; j++) {
            allResume[j] = storage[j];
        }
        return allResume;
    }

    int size() {
        return size;
    }
}