public class Entry<K, V> {
    private K key;
    private V value;

     public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String getEntry(){
         if(this.key == null || this.value == null){
             return "null";
         }
        return (String)this.key + ":" + this.value;
    }
}
