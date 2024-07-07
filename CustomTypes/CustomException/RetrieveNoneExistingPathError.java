package CustomTypes.CustomException;

public class RetrieveNoneExistingPathError extends Exception {
    public RetrieveNoneExistingPathError(final String traf_flow, final String none_existent_intersection) {
        super("The Traffic Flow retrieve doesn't exist\n"+traf_flow+"\nCould not find intersection connecting \""+none_existent_intersection+"\"");
    }
}
