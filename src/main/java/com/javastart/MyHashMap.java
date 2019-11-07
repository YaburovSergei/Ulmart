package com.javastart;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static int initialCapacity = 16;

    private Node<K, V>[] nodeTable;

    public MyHashMap() {
        nodeTable = new Node[initialCapacity];
    }

    public MyHashMap(int capacity) {
        initialCapacity = capacity;
        nodeTable = new Node[initialCapacity];
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("Zero key not allowed");
        }

        int hashValue = hashValue(key);
        Node<K, V> node = new Node<K, V>(key, value, null);
        if (nodeTable[hashValue] == null) {
            nodeTable[hashValue] = node;
        } else {
            Node<K, V> previous = null;
            Node<K, V> current = nodeTable[hashValue];
            while (current != null) {
                if (current.k.equals(key)) {
                    if (previous == null) {
                        node.next = current.next;
                        nodeTable[hashValue] = node;
                    } else {
                        node.next = current.next;
                        node.next = node;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = node;
        }
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        int hashValue = hashValue(key);
        if (nodeTable[hashValue] == null) {
            return null;
        } else {
            Node<K, V> temp = nodeTable[hashValue];
            while (temp != null) {
                if (temp.k.equals(key)) {
                    return temp.v;
                }
                temp = temp.next;
            }
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            return null;
        }
        int hashValue = hashValue(key);
        if (nodeTable[hashValue] == null) {
            return null;
        } else {
            Node<K, V> previous = null;
            Node<K, V> current = nodeTable[hashValue];
            while (current != null) {
                if (current.k.equals(key)) {
                    if (previous == null) {
                        nodeTable[hashValue] = nodeTable[hashValue].next;
                        return current.getV();
                    } else {
                        previous.next = current.next;
                        return previous.getV();
                    }
                }
                previous = current;
                current = current.next;
            }
            return current.getV();
        }
    }

    public int size() {
        int count = 0;
        for (int i = 0; i < nodeTable.length; i++) {
            if (nodeTable[i] != null) {
                int nodeCount = 0;
                for (Node<K, V> e = nodeTable[i]; e.next != null; e = e.next) {
                    nodeCount++;
                }
                count += nodeCount;
                count++;
            }
        }
        return count;
    }

    public boolean containsKey(K key) {
        K keys[] = keySet();
        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i])) {
                return true;
            }

        }
        return false;
    }

    public K[] keySet() {
        int length = 0;
        for (int j = 0; j < nodeTable.length; j++) {
            if (nodeTable[j] != null)
                length++;
        }
        K keys[] = (K[]) new Object[length];
        int k = 0;
        for (int j = 0; j < nodeTable.length; j++) {
            if (nodeTable[j] != null) {
                keys[k] = nodeTable[j].getK();
                k++;
            }

        }

        return keys;
    }

    private int hashValue(K key) {
        return key.hashCode() % initialCapacity;
    }

    private static class Node<K, V> {
        private K k;
        private V v;
        private Node<K, V> next;

        public Node(K k, V v, Node<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }
    }
}



