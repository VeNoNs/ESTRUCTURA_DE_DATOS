package Controller;

import Interfaces.IListaEnlazada;
import Interfaces.ITablasHash;
import Models.HashNode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * Implementación de una tabla hash.
 *
 * @param <K> el tipo de clave
 * @param <V> el tipo de valor
 */
public class ImplTablaHash<K, V> implements ITablasHash<K, V> {
    private HashNode<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public ImplTablaHash() {
        this.table = new HashNode[10]; // Capacidad inicial pequeña
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);

        // Si el bucket está vacío, añadir directamente
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            HashNode<K, V> current = table[index];
            // Recorrer la lista enlazada en el bucket
            while (true) {
                if (current.getKey().equals(key)) {
                    // Si la clave ya existe, actualizar el valor
                    current.setValue(value);
                    return;
                }
                if (current.getNext() == null) {
                    // Añadir al final de la lista enlazada
                    current.setNext(newNode);
                    break;
                }
                current = current.getNext();
            }
        }
        size++;
        
        // Redimensionar si el factor de carga supera 0.7
        if ((1.0 * size) / table.length >= 0.7) {
            resize();
        }
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = table[index];

        // Recorrer la lista enlazada en el bucket
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null; // Clave no encontrada
    }

    @Override
    public void remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = table[index];
        HashNode<K, V> prev = null;

        // Recorrer la lista enlazada en el bucket
        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    // Eliminar el nodo de la cabeza del bucket
                    table[index] = current.getNext();
                } else {
                    // Eliminar el nodo intermedio o final
                    prev.setNext(current.getNext());
                }
                size--;
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        HashNode<K, V>[] oldTable = table;
        table = new HashNode[oldTable.length * 2];
        size = 0;

        // Reinsertar todos los elementos en la nueva tabla
        for (HashNode<K, V> headNode : oldTable) {
            while (headNode != null) {
                put(headNode.getKey(), headNode.getValue());
                headNode = headNode.getNext();
            }
        }
    }

    /*public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (HashNode<K, V> node : table) {
            while (node != null) {
                set.add(node.toEntry()); // Usar el método toEntry
                node = node.getNext();
            }
        }
        return set;
    }*/
    
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new TreeSet<>((e1, e2) -> ((Comparable<K>) e1.getKey()).compareTo(e2.getKey()));
        for (HashNode<K, V> node : table) {
            while (node != null) {
                set.add(node.toEntry());
                node = node.getNext();
            }
        }
        return set;
    }
}
