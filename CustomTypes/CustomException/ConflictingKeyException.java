package CustomTypes.CustomException;

public class ConflictingKeyException extends Exception {
    public ConflictingKeyException(final String key) {
        super("Key value of " + key + " already exist within the instance of HashMap class");
    }
}
