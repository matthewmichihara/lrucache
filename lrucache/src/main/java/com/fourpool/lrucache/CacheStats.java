package com.fourpool.lrucache;

import java.util.List;

/**
 * Auxiliary cache data.
 */
public class CacheStats {
  public final LruLinkedHashMap<List<Object>, Object> cache;
  public int hits;
  public int misses;

  CacheStats(LruLinkedHashMap<List<Object>, Object> cache, int hits, int misses) {
    this.cache = cache;
    this.hits = hits;
    this.misses = misses;
  }
}
