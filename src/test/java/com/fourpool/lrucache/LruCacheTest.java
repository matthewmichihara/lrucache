package com.fourpool.lrucache;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.fourpool.lrucache.Caches.CACHE_STATS;
import static org.junit.Assert.*;

public class LruCacheTest {
  /**
   * The annotated method we'll be testing.
   */
  @LruCache(maxSize = 3, key = "test")
  private int sum(int a, int b) {
    return a + b;
  }

  @Before
  public void setup() {
    CACHE_STATS.clear();
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Test
  public void testCache() {
    // Invoke annotated method. This should result in a cache miss,
    // and a new entry in the cache.
    int sum1 = sum(1, 2);
    assertEquals(3, sum1);
    assertTrue(CACHE_STATS.containsKey("test"));
    var cacheStats = CACHE_STATS.get("test");
    assertEquals(0, cacheStats.hits);
    assertEquals(1, cacheStats.misses);
    assertEquals(1, cacheStats.cache.size());

    // Invoke the annotated method again with new parameters.
    // This should result in another cache miss, and a new
    // entry in the cache.
    int sum2 = sum(3, 4);
    assertEquals(7, sum2);
    assertEquals(0, cacheStats.hits);
    assertEquals(2, cacheStats.misses);
    assertEquals(2, cacheStats.cache.size());

    // Invoke the annotated method again with previously used parameters.
    // This should result in a cache hit and result in no new
    // cache entry.
    int sum3 = sum(1, 2);
    assertEquals(3, sum3);
    assertEquals(1, cacheStats.hits);
    assertEquals(2, cacheStats.misses);
    assertEquals(2, cacheStats.cache.size());

    // Invoke twice more to trigger LRU cache eviction.
    // This should result in two more cache misses.
    // Cache size should be 3 with the LRU entry evicted.
    sum(5, 0);
    sum(8, 9);
    assertEquals(1, cacheStats.hits);
    assertEquals(4, cacheStats.misses);
    assertEquals(3, cacheStats.cache.size());
    assertTrue(cacheStats.cache.containsKey(List.of(1, 2)));
    // This one is least recently used and should have been evicted.
    assertFalse(cacheStats.cache.containsKey(List.of(3, 4)));
    assertTrue(cacheStats.cache.containsKey(List.of(5, 0)));
    assertTrue(cacheStats.cache.containsKey(List.of(8, 9)));
  }
}
