package com.javastart;

public interface MyMap<K, V> {

    void put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    int size();

    K[] keySet();


}
