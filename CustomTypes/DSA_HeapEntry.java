package CustomTypes;
public class DSA_HeapEntry {
    private float priority;
    private Object value;

    public DSA_HeapEntry(final float p_priority, final Object p_value) {
        priority = p_priority;
        value = p_value;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(final float p_priority) {
        priority = p_priority;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object p_value) {
        value = p_value;
    }

}
