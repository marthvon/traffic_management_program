package CustomTypes.CustomException;

public class IncorrectShortestPathError extends Exception {
    public IncorrectShortestPathError(final String presumed_shortest_path, final String actual_shortest_path) {
        super(
            "\n\tIncorrect shortest path of " + presumed_shortest_path +
            " has been retrieved when other path of " + actual_shortest_path
        );
    }
}
