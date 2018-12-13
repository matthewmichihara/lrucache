package com.fourpool.lrucache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.*;

@Aspect
public class LruCacheAspect {
  /**
   * Maps function signature to maps of parameters to return values.
   */
  private static final Map<String, Map<List<Object>, Object>> CACHES = new HashMap<>();

  @Around("execution(* *(..)) && @annotation(LruCache)")
  public Object checkCache(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("CACHES=" + CACHES);
    String cacheKey = joinPoint.getSignature().toLongString();
    Map<List<Object>, Object> cache = CACHES.get(cacheKey);
    if (cache == null) cache = new HashMap<>();
    CACHES.put(cacheKey, cache);

    List<Object> parameterKey = Arrays.asList(joinPoint.getArgs());
    if (cache.containsKey(parameterKey)) {
      return cache.get(parameterKey);
    }

    Object result = joinPoint.proceed();
    cache.put(parameterKey, result);
    return result;
  }
}