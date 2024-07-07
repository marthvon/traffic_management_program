package CustomTypes;

import CustomTypes.CustomException.ConflictingKeyException;
import CustomTypes.CustomException.EmptyListException;
import CustomTypes.CustomException.NonExistentNodeLabel;
//undirected
public class DSA_GraphMatrix implements IDSA_Graph {

    private HashMap LookupTable;
    private DoubleEndedLinkedList freeIndex;
    private boolean[][] Matrix;

    public DSA_GraphMatrix() {
        LookupTable = new HashMap();
        freeIndex = new DoubleEndedLinkedList();
        Matrix = new boolean[5][5];
    }

    public String[] getAllNodeLabels() {
        String[] res = new String[vertexCount()];
        LookupTable.getKeyList(res);
        return res;
    }

    public void displayAsList() {
        String[] indexToLabel = new String[Matrix.length];
        LookupTable.getKeyList(indexToLabel);
        
        for(int i = 0; i < Matrix.length; ++i) {
            if(indexToLabel[i] == null)
                continue;
            System.out.print(indexToLabel[i] + " => ");
            for(int j = 0; j < Matrix.length; ++j)
                if(Matrix[i][j])
                    System.out.print(indexToLabel[j] + ", ");
            System.out.println("\b");
        }
    }

    public void displayAsMatrix() {
        String[] indexToLabel = new String[Matrix.length];
        LookupTable.getKeyList(indexToLabel);

        System.out.print("  ");
        for(String label: indexToLabel)
            if(label != null)
                System.out.print(label + " ");
        System.out.println();

        for(int i = 0; i < Matrix.length; ++i) {
            if(indexToLabel[i] == null)
                continue;
            System.out.print(indexToLabel[i] + " ");
            for(int j = 0; j < Matrix.length; ++j)
                if(indexToLabel[j] != null)
                    System.out.print(Matrix[i][j]? "1 ":"0 ");
            System.out.println();
        }
    }

    public void addNode(String label) throws ConflictingKeyException {
        try {
            LookupTable.put(label, freeIndex.isEmpty()? LookupTable.size() : (Integer)freeIndex.popFirst());
        } catch(EmptyListException e) {}
        
        if(LookupTable.size() < Matrix.length)
            return;
        
        final int new_size = Matrix.length*2;
        boolean[][] temp = new boolean[new_size][new_size];
        for(int i = 0; i < Matrix.length; ++i)
            for(int j = 0; j < Matrix.length; ++j)
                temp[i][j] = Matrix[i][j];
        Matrix = temp;
    }

    public void deleteNode(String label) throws NonExistentNodeLabel {
        HashEntry entry = LookupTable.get(label);
        if(entry == null)
            throw new NonExistentNodeLabel(label, "DSA_GraphMatrix");
        final int index = (Integer)entry.getValue();
        freeIndex.insertLast(index);
        for(int i = 0; i < Matrix.length; ++i) {
            Matrix[index][i] = false;
            Matrix[i][index] = false;
        }
        LookupTable.remove(label);
    }

    public void addEdge(String label1, String label2) throws NonExistentNodeLabel {
        Integer index1 = (Integer)LookupTable.get(label1).getValue();
        if(index1 == null)
            throw new NonExistentNodeLabel(label1, "DSA_GraphMatrix");
        Integer index2 = (Integer)LookupTable.get(label2).getValue();
        if(index2 == null)
            throw new NonExistentNodeLabel(label2, "DSA_GraphMatrix");
        Matrix[index1][index2] = true;
        Matrix[index2][index1] = true;
    }

    public void deleteEdge(String label1, String label2) throws NonExistentNodeLabel {
        Integer index1 = (Integer)LookupTable.get(label1).getValue();
        if(index1 == null)
            throw new NonExistentNodeLabel(label1, "DSA_GraphMatrix");
        Integer index2 =(Integer) LookupTable.get(label2).getValue();
        if(index2 == null)
            throw new NonExistentNodeLabel(label2, "DSA_GraphMatrix");
        Matrix[index1][index2] = false;
        Matrix[index2][index1] = false;
    }

    public String BreadthFirstSearch(final String start_node) throws NonExistentNodeLabel {
        Integer index = (Integer)LookupTable.get(start_node).getValue();
        if(index == null)
            throw new NonExistentNodeLabel(start_node, "DSA_GraphMatrix");

        String res = start_node + " ";
        DoubleEndedLinkedList list = new DoubleEndedLinkedList();
        boolean[] visited = new boolean[Matrix.length];
        
        String[] indexToLabel = new String[Matrix.length];
        LookupTable.getKeyList(indexToLabel);
        
        list.insertLast(index);
        try {
            while(!list.isEmpty()) {
                final int idx = (Integer)(list.popFirst());
                if(visited[idx])
                    continue;
                res += indexToLabel[idx] + " ";
                visited[idx] = true;
                for(int i = 0; i < Matrix.length; ++i)
                    if(indexToLabel[i] != null && !visited[i] && Matrix[idx][i])
                        list.insertLast(i);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return res;
    }

    public String DepthFirstSearch(final String start_node) throws NonExistentNodeLabel {
        Integer index = (Integer)LookupTable.get(start_node).getValue();
        if(index == null)
            throw new NonExistentNodeLabel(start_node, "DSA_GraphMatrix");

        String res = "";
        SingleEndedLinkedList list = new SingleEndedLinkedList();
        boolean[] visited = new boolean[Matrix.length];

        String[] indexToLabel = new String[Matrix.length];
        LookupTable.getKeyList(indexToLabel);

        list.insertFirst(index);
        try {
            while(!list.isEmpty()) {
                final int idx = (Integer)(list.popFirst());
                if(visited[idx])
                    continue;
                visited[idx] = true;
                res += indexToLabel[idx] + " ";
                for(int i = 0; i < Matrix.length; ++i)
                    if(indexToLabel[i] != null && !visited[i] && Matrix[idx][i])
                        list.insertFirst(i);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public boolean hasVertex(String label) {
        return LookupTable.get(label) !=  null;
    }

    public boolean isAdjacent(String label1, String label2) {
        Integer index1 = (Integer)LookupTable.get(label1).getValue();
        if(index1 == null)
            return false;
        Integer index2 = (Integer)LookupTable.get(label2).getValue();
        if(index2 == null)
            return false;
        return Matrix[index1][index2];
    }

    public String[] getAdjacent(String label) throws NonExistentNodeLabel {
        Integer index = (Integer)LookupTable.get(label).getValue();
        if(index == null)
            throw new NonExistentNodeLabel(label, "DSA_GraphMatrix");
        
        String[] indexToLabel = new String[Matrix.length];
        LookupTable.getKeyList(indexToLabel);

        DoubleEndedLinkedList list = new DoubleEndedLinkedList();
        for(int i = 0; i < Matrix.length; ++i)
            if(Matrix[index][i])
                list.insertLast(indexToLabel[i]);    
        
        String[] res = new String[list.size()];
        int res_tail = -1;
        try {
            while(!list.isEmpty())
                res[++res_tail] = (String)(list.popFirst());
        } catch(Exception e) { System.err.println(e.getMessage()); }
        
        return res;
    }

    public int edgeCount() {
        int res = 0;
        for(int i = 0; i < Matrix.length; ++i)
            for(int j = 0; i < Matrix.length; ++j)
                if(Matrix[i][j])    
                    ++res;
        return res/2;
    }

    public int vertexCount() {
        return LookupTable.size() - freeIndex.size();
    }
};
