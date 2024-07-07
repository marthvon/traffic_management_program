package CustomTypes.CustomException;

public class EmptyListException extends Exception {
    public EmptyListException(final String class_name) {
        super("Call to access element in instance of " + class_name + " class currently doesn't contain any elements");
    }
}
