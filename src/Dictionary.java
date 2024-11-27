public class Dictionary<K, V> implements DictionaryInterface<K, V> {
    private Entry<K, V>[] dictionary;
    private int numEntries;
    private boolean integrityOK = false;
    private final static int DEFAULT_CAPACITY = 25;
    private final static int MAX_CAPACITY = 1000;

    public Dictionary(){
        Entry<K, V>[] tempDictionary = (Entry<K, V>[])new Entry[DEFAULT_CAPACITY];
        this.dictionary = tempDictionary;
        this.numEntries = 0;
        integrityOK = true;
    }
    @Override
    public V put(K key, V value){
        if ((key == null) || (value == null)){
            throw new IllegalArgumentException("Cannot add null to this dictionary.");}
        else{
            V result = null;
            int keyIndex = locateIndex(key);
            if(keyIndex < numEntries){
                result = dictionary[keyIndex].getValue();
                dictionary[keyIndex].setValue(value);
            } else {
                dictionary[numEntries] = new Entry<>(key, value);
                numEntries++;
                ensureCapacity();
            }
            return result;
        }
    }

    public V remove(K key){
        V result = null;;
        int keyIndex = locateIndex(key);
        if(keyIndex < numEntries){
             result = this.dictionary[keyIndex].getValue();
            this.dictionary[keyIndex] = this.dictionary[numEntries - 1];
            this.dictionary[numEntries - 1] = null;
            numEntries--;
        }
        return result;
    }

    public V get(K key) {
        int keyIndex = locateIndex(key);
        if (keyIndex < numEntries) {
            return dictionary[keyIndex].getValue();
        } else {
            return null;
        }
    }

    public Entry<K, V>[] getArray(){
        return this.dictionary;
    }


    @Override
    public boolean contains(K key){
        boolean found = false;
        for (Entry<K, V> dictKey: this.dictionary) {
            if(dictKey == null){
                break;
            }

            if(dictKey.getKey().equals(key)){
                found = true;
            }
        }
        return found;
    }

    public int size(){
        return this.dictionary.length;
    }

    private int locateIndex(K key)
    {
        int index = 0;
        while ( (index < numEntries) &&
                !key.equals(dictionary[index].getKey()) )
            index++;
        return index;
    }

    private void ensureCapacity() {
        if (numEntries >= dictionary.length) {
            int newCapacity = Math.min(dictionary.length * 2, MAX_CAPACITY);
            if (newCapacity > dictionary.length) {
                Entry<K, V>[] tempDictionary = (Entry<K, V>[])new Entry[newCapacity];
                System.arraycopy(dictionary, 0, tempDictionary, 0, numEntries);
                dictionary = tempDictionary;
            } else {
                throw new IllegalStateException("Cannot exceed maximum capacity.");
            }
        }
    }
}




