package com.fourpool.lrucache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Caches {
  /**
   * Maps function signature to maps of parameters to return values.
   */
  static final Map<String, LruLinkedHashMap<List<Object>, Object>> CACHES = new HashMap<>();
  static final Map<String, CacheStats> CACHE_STATS = new HashMap<>();
}
