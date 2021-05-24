/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int resumeStorageSize = 0;

    void clear() {
        if (resumeStorageSize > 0){
            for (int i = 0; i < resumeStorageSize; i++) {
                storage[i] = null;
            }
        resumeStorageSize = 0;
        }
    }

    void save(Resume r) {
                storage[resumeStorageSize] = r;
                resumeStorageSize++;
    }

    Resume get(String uuid) {
        Resume resumeForGet = null;
        for (int i = 0; i < resumeStorageSize; i++) {
            if (storage[i].toString().equals(uuid)) {
                resumeForGet = storage[i];
                break;
            }
        }
        return resumeForGet;
    }

    void delete(String uuid) {
        int countForMove = 0;
        boolean isFindResume = false;

        for (int i = 0; i < resumeStorageSize; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                countForMove = i;
                isFindResume = true;
                break;
            }
        }
        if (isFindResume) {
            for (int j = countForMove + 1; j < resumeStorageSize; j++) {
                if (storage[j] != null) {
                    storage[j - 1] = storage[j];
                    storage[j] = null;
                } else {
                    break;
                }
            }
            resumeStorageSize--;
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resultStorage = new Resume[resumeStorageSize];
        for (int j = 0; j < resultStorage.length; j++) {
            resultStorage[j] = storage[j];
        }
        return resultStorage;
    }

    int size() {
        return resumeStorageSize;
    }
}