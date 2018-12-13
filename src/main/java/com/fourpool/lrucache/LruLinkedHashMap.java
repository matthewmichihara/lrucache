package com.fourpool.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

class LruLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
  final int capacity; // Maximum number of items in the cache.

  LruLinkedHashMap(int capacity) {
    super(capacity + 1, 1, true);
    this.capacity = capacity;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry entry) {
    return (size() > this.capacity);
  }
}
