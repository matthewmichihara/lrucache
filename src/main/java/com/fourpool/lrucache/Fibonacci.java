package com.fourpool.lrucache;

class Fibonacci {
  int calculate(int n) {
    System.out.println("calculate(" + n + ")");
    if (n == 1) return 1;
    if (n == 2) return 1;
    return calculate(n - 1) + calculate(n - 2);
  }

  @LruCache
  int cachedCalculate(int n) {
    System.out.println("cachedCalculate(" + n + ")");
    if (n == 1) return 1;
    if (n == 2) return 1;
    return cachedCalculate(n - 1) + cachedCalculate(n - 2);
  }
}
