package CustomTypes;
import CustomTypes.CustomException.ConflictingKeyException;

public class HashMap {
    private HashEntry hashArray[];
    private int count = 0;

    public HashMap() {
        hashArray = new HashEntry[11];
    }

    public HashMap(final int size) {
        hashArray = new HashEntry[nextPrime((int)(size/0.6)+1)];
    }

    public void getKeyList(String[] out) {
        for(int i = 0; i < hashArray.length; ++i)
            if(hashArray[i] != null)
                out[(Integer)hashArray[i].getValue()] = hashArray[i].getKey();
    }

    public int hash(final String key) {
        long hashIdx = 2166136261L;
        for(int i = 0; i < key.length(); ++i) {
            hashIdx *= 16777619;
            hashIdx ^= (long)(key.charAt(i));
        }
        if(hashIdx < 0)
            hashIdx *= -1;
        return (int)(hashIdx % hashArray.length);
    }

    static private int MAX_STEP = 7;
    private int stepHash(final String key) {
        return MAX_STEP - (hash(key) % MAX_STEP);
    }

    private int modPow(int base, int exponent, int modulus) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % modulus;
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }

    static private final int MAX_PRIMALITY_TEST = 5;
    private int nextPrime(int num) {
        if((num % 2) == 0)
            ++num;

        while(true) {
            boolean isPrime = true;
            for(int i = 0; i < MAX_PRIMALITY_TEST; ++i) {
                int a = (int)(Math.random()*(num-1))+1;
                if( modPow(a, num-1, num) == 1)
                    continue;
                isPrime = false;
                break;
            }
            if(isPrime)
                return num;
            num += 2;
        }
    }

    public void put(final String key, final Object value) throws ConflictingKeyException {
        if(getLoadFactor() > 0.6)
            resize(hashArray.length*2);
        put(new HashEntry(key, value));
    }

    public void put(HashEntry entry) throws ConflictingKeyException {
        int index = hash(entry.getKey());
        final int step = stepHash(entry.getKey());
        
        while(hashArray[index] != null) {
            if(hashArray[index].getKey() == entry.getKey())
                throw new ConflictingKeyException(entry.getKey());
            index += step;
            index %= hashArray.length;
        }
        hashArray[index] = entry;
        
        ++count;
    }

    public HashEntry get(final String key) {
        final int index = hash(key);
        final int step = stepHash(key);
        int curr = index;
        while(true) {
            HashEntry item = hashArray[curr];
            if(item == null)
                return null;
            if(item.getKey().equals(key))
                return item;
            curr += step;
            curr %= hashArray.length;
            if(curr == index)
                return null;
        }
    }

    public boolean hasKey(final String key) {
        if(get(key) == null)
            return false;
        return true;
    }

    public void remove(final String key) {
        final int index = hash(key);
        final int step = stepHash(key);
        int curr = index;
        while(true) {
            HashEntry item = hashArray[curr];
            if(item == null)
                return;
            if(item.getKey() == key) {
                hashArray[curr] = null;
                --count;
                return;
            }
            curr += step;
            curr %= hashArray.length;
            if(curr == index)
                return;
        }
    }

    private float getLoadFactor() {
        return (float)(count) / hashArray.length;
    }

    public HashEntry[] getArray() {
        return hashArray;
    }
    public int size() {
        return count;
    }

    public void resize(int size) {
        size = nextPrime(size);
        HashEntry[] orig = hashArray;
        hashArray = new HashEntry[size];

        try {
            count = 0;
            for(int i = 0; i < orig.length; ++i)
                if(orig[i] != null)
                    put(orig[i]); 
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void display() {
        for(int i = 0; i < hashArray.length; ++i)
            if(hashArray[i] != null)
                System.out.println(hashArray[i].getKey() + ": " + (hashArray[i].getValue()));
            else
                System.out.println("\t---\tnull\t---\t");
    }
}
