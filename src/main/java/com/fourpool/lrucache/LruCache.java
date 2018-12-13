package com.fourpool.lrucache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LruCache {
  public int maxSize() default 1024;

  /**
   * Optionally provide a key to index this cache by
   * in {@code Caches.CACHE_STATS}. This should be unique.
   */
  public String key() default "";
}
