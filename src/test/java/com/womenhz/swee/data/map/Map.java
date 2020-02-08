package com.womenhz.swee.data.map;

public interface Map<K, V> {

    void put(K k, V v);

    V get(K k);

    void remove(K k);

}
