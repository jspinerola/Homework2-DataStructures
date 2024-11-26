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
        V val = null;
        return val;
    }

    public V get(K key){
        V val = null;
        return val;
    }

    public Entry<K, V>[] getArray(){
        return this.dictionary;
    }

    public boolean contains(K key){
        return true;
    }

    public int size(){
        return 1;
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




