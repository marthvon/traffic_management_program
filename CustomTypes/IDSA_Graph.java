package CustomTypes;
import CustomTypes.CustomException.ConflictingKeyException;
import CustomTypes.CustomException.NonExistentNodeLabel;

public interface IDSA_Graph {
    public void displayAsList();
    public void displayAsMatrix();
    public void addNode(String label) throws ConflictingKeyException;
    public void deleteNode(String label) throws NonExistentNodeLabel;
    public void addEdge(String label1, String label2) throws NonExistentNodeLabel;
    public void deleteEdge(String label1, String label2) throws NonExistentNodeLabel;
    public String BreadthFirstSearch(final String start_node) throws NonExistentNodeLabel;
    public String DepthFirstSearch(final String start_node) throws NonExistentNodeLabel;
    public boolean hasVertex(String label);
    public boolean isAdjacent(String label1, String label2);
    public String[] getAdjacent(String label) throws NonExistentNodeLabel;
    public int edgeCount();
    public int vertexCount();
}
