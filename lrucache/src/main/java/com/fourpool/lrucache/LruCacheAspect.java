package com.fourpool.lrucache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

import static com.fourpool.lrucache.Caches.CACHES;
import static com.fourpool.lrucache.Caches.CACHE_STATS;

@Aspect
public class LruCacheAspect {
  /**
   * Executes around methods annotated with {@code LruCache}, adding
   * a memoization layer.
   */
  @SuppressWarnings("unused")
  @Around("execution(* *(..)) && @annotation(LruCache)")
  public Object checkCache(ProceedingJoinPoint joinPoint) throws Throwable {
    var methodKey = methodKey(joinPoint);

    // Find or create the LRU cache for this method invocation.
    var cache = CACHES.computeIfAbsent(methodKey, k -> {
      int maxSize = cacheMaxSize(joinPoint);
      return new LruLinkedHashMap<>(maxSize);
    });

    // Find or create the LRU cache stats for this method invocation.
    var cacheStats = CACHE_STATS.computeIfAbsent(methodKey, k -> {
      //noinspection CodeBlock2Expr
      return new CacheStats(cache, 0, 0);
    });

    // Check cache to see if we've already invoked this method with
    // these parameters. If so, return early with the cached result.
    var parameterKey = parameterKey(joinPoint);
    if (cache.containsKey(parameterKey)) {
      cacheStats.hits++;
      return cache.get(parameterKey);
    }

    // Since we're here, there was no cached result. Invoke the
    // annotated method and cache the result before returning it.
    var result = joinPoint.proceed();
    cacheStats.misses++;
    cache.put(parameterKey, result);
    return result;
  }

  /**
   * Creates the method key that we index LRU caches by.
   * By default this is the annotated method's signature but can be
   * overridden by the {@code LruCache#key} field.
   */
  private static String methodKey(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    LruCache annotation = signature.getMethod().getAnnotation(LruCache.class);
    var annotationKey = annotation.key();
    if (!annotationKey.isEmpty()) return annotationKey;

    return signature.toLongString();
  }

  /**
   * Returns the LRU cache's maximum size from the annotation.
   */
  private static int cacheMaxSize(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    LruCache annotation = signature.getMethod().getAnnotation(LruCache.class);
    return annotation.maxSize();
  }

  /**
   * Creates the parameter key that we index LRU cache entries by.
   */
  private static List<Object> parameterKey(JoinPoint joinPoint) {
    return List.of(joinPoint.getArgs());
  }
}