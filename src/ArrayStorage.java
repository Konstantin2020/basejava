/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            } else {
                break;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Object get(String uuid) {
        Resume resumeForGet = null;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].toString().equals(uuid)) {
                resumeForGet = storage[i];
                break;
            }
        }
        return resumeForGet;
    }

    void delete(String uuid) {
        int countForMove = 0;
        boolean isFindResume = false;

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].toString().equals(uuid)) {
                storage[i] = null;
                countForMove = i;
                isFindResume = true;
                break;
            }
        }
        if (isFindResume) {
            for (int j = countForMove + 1; j < storage.length; j++) {
                if (storage[j] != null) {
                    storage[j - 1] = storage[j];
                    storage[j] = null;
                } else {
                    break;
                }

            }
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int resumeForGetAll = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                resumeForGetAll = i;
                break;
            }
        }
        Resume[] resultStorage = new Resume[resumeForGetAll];
        for (int j = 0; j < resultStorage.length; j++) {
            resultStorage[j] = storage[j];
        }
        return resultStorage;
    }

    int size() {
        int resumeStorageSize = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                resumeStorageSize = i;
                break;
            }
        }
        return resumeStorageSize;
    }
}