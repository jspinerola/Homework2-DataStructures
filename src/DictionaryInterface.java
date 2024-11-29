public interface DictionaryInterface<K, V> {
    public V put(K key, V value);

    public V remove(K key);

    public V get(K key);

    public int size();

    public boolean contains(K key);

}
