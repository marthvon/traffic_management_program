package CustomTypes;

public class HashEntry {
    private String key;
    private Object value;

    public HashEntry() {
        key = "";
        value = null;
    }

    public HashEntry(final String p_key, final Object p_value) {
        key = p_key;
        value = p_value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object p_value) {
        value = p_value;
    }
}