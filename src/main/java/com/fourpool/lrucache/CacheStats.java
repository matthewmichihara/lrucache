package com.fourpool.lrucache;

import java.util.List;

class CacheStats {
  final LruLinkedHashMap<List<Object>, Object> cache;
  int hits;
  int misses;

  CacheStats(LruLinkedHashMap<List<Object>, Object> cache, int hits, int misses) {
    this.cache = cache;
    this.hits = hits;
    this.misses = misses;
  }
}
