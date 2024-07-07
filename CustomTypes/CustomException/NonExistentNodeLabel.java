package CustomTypes.CustomException;

public class NonExistentNodeLabel extends Exception {
    public NonExistentNodeLabel(final String label, final String class_name) {
        super("Passed in the label \"" + label + "\" of a node in the instance of <" + class_name + "> class doesn't exist");
    }
}
