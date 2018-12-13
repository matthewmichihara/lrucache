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
  @SuppressWarnings("unused")
  @Around("execution(* *(..)) && @annotation(LruCache)")
  public Object checkCache(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("CACHES=" + CACHES);

    var methodKey = methodKey(joinPoint);

    var cache = CACHES.computeIfAbsent(methodKey, k -> {
      int maxSize = cacheMaxSize(joinPoint);
      return new LruLinkedHashMap<>(maxSize);
    });

    var cacheStats = CACHE_STATS.computeIfAbsent(methodKey, k -> new CacheStats(cache, 0, 0));

    var parameterKey = List.of(joinPoint.getArgs());
    if (cache.containsKey(parameterKey)) {
      cacheStats.hits++;
      return cache.get(parameterKey);
    }

    var result = joinPoint.proceed();
    cacheStats.misses++;
    cache.put(parameterKey, result);
    return result;
  }

  private static String methodKey(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    LruCache annotation = signature.getMethod().getAnnotation(LruCache.class);
    var annotationKey = annotation.key();
    if (!annotationKey.isEmpty()) return annotationKey;

    return signature.toLongString();
  }

  private static int cacheMaxSize(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    LruCache annotation = signature.getMethod().getAnnotation(LruCache.class);
    return annotation.maxSize();
  }
}