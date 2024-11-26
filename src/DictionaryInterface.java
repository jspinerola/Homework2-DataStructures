public interface DictionaryInterface<K, V> {
    public V put(K key, V value);

    public V remove(K key);

    public V get(K key);

    public boolean contains(K key);

    public int size();

}
