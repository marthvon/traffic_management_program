package CustomTypes;

public class DSA_HeapMax {
    DSA_HeapEntry[] entry;
    int capacity = 10;
    int size = 0;

    public DSA_HeapMax() {
        entry = new DSA_HeapEntry[capacity];
    }

    public DSA_HeapMax(final DSA_HeapMax other) {
        capacity = other.capacity;
        size = other.size;
        entry = new DSA_HeapEntry[other.entry.length];
        for(int i = 0; i < other.entry.length; ++i)
            entry[i] = other.entry[i];
    }

    public DSA_HeapMax(float[] priority, Object[] value) {
        capacity = priority.length;
        entry = new DSA_HeapEntry[capacity];
        for(int i = 0; i < priority.length; ++i, ++size)
            entry[size] = new DSA_HeapEntry(priority[i], value[i]);
        for(int i = (size/2)-1; i >=0; --i)
            trickleDown(i);
    }

    public void add(final float priority, final Object value) {
        if(size == capacity) {
            capacity *= 2;
            DSA_HeapEntry[] temp = new DSA_HeapEntry[capacity];
            for(int i = 0; i < entry.length; ++i)
                temp[i] = entry[i];
            entry = temp;
        }
        entry[trickleUp(priority)] = new DSA_HeapEntry(priority, value);
        ++size;
    }

    public DSA_HeapEntry remove() {
        DSA_HeapEntry temp = entry[0];
        entry[0] = entry[--size];
        entry[size] = null;
        trickleDown(0);
        return temp;
    }

    public void display() {
        for(DSA_HeapEntry e : entry) 
            System.out.println("{ " + e.getPriority() + ", " + e.getValue() + " }");
    }

    public int getSize() {
        return size;
    }

    private int trickleUp(final float priority) {
        int curr = size;
        while(true) {
            final int parent = parentIdx(curr);
            if(curr == 0 || priority <= entry[parent].getPriority())
                return curr;
            entry[curr] = entry[parent];
            curr = parent;
        }
    }

    private void trickleDown(int index) {
        while(true) {
            final int left = leftChildIdx(index);
            final int right = left+1;
            if(left >= size || entry[left] == null)
                return;
            int max = (
                right >= size || entry[right] == null ||
                entry[left].getPriority() >= entry[right].getPriority()
            )? left : right;
            
            if(entry[max].getPriority() <= entry[index].getPriority())
                return;
            swap(index, max);
            index = max;
        }
    }

    private void swap(final int index1, final int index2) {
        DSA_HeapEntry temp = entry[index1];
        entry[index1] = entry[index2];
        entry[index2] = temp;
    }

    static private int leftChildIdx(final int index) {
        return index*2 + 1;
    }

    static private int rightChildIdx(final int index) {
        return index*2 + 2;
    }

    static private int parentIdx(final int index) {
        return (index-1) / 2;
    }
}
