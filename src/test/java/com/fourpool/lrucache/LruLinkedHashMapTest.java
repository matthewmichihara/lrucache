package com.fourpool.lrucache;

import org.junit.Test;

import static org.junit.Assert.*;

public class LruLinkedHashMapTest {
  @Test
  public void testPutGet() {
    var map = new LruLinkedHashMap<String, Integer>(10);
    map.put("foo", 1);
    assertEquals(1, (int) map.get("foo"));
  }

  @Test
  public void testCapacity() {
    var map = new LruLinkedHashMap<String, Integer>(3);
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("d", 4);

    assertEquals(3, map.size());
    assertFalse(map.containsKey("a"));
    assertTrue(map.containsKey("b"));
    assertTrue(map.containsKey("c"));
    assertTrue(map.containsKey("d"));
  }

  @Test
  public void testLeastRecentlyAccessedRemoved() {
    var map = new LruLinkedHashMap<String, Integer>(3);
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.get("a");
    map.put("d", 4);

    assertEquals(3, map.size());
    assertTrue(map.containsKey("a"));
    assertFalse(map.containsKey("b"));
    assertTrue(map.containsKey("c"));
    assertTrue(map.containsKey("d"));
  }
}
