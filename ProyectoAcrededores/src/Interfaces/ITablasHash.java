package Interfaces;

/**
 *
 * @author Frank
 */
public interface ITablasHash<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    boolean containsKey(K key);
    int size();
    boolean isEmpty();
}
