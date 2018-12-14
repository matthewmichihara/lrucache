package com.fourpool.lrucache.sample;

import com.fourpool.lrucache.Caches;

public class Main {
  public static void main(String[] args) {
    Fibonacci fib = new Fibonacci();

    // Un-annotated version.
    System.out.println("7th fibonacci number: " + fib.calculate(7));
    System.out.println();

    // Annotated version.
    System.out.println("7th fibonacci number: " + fib.cachedCalculate(7));
    var cacheStats = Caches.CACHE_STATS.get("fib");
    System.out.println(cacheStats);
  }
}

