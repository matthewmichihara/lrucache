package com.fourpool.lrucache.samplemavenjitpack;

import com.fourpool.lrucache.LruCache;

public class Main {
  @LruCache
  static int sum(int a, int b) {
    System.out.println("sum called");
    return a + b;
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
    System.out.println(sum(1, 2));
    System.out.println(sum(1, 2));
    System.out.println(sum(1, 2));
    System.out.println(sum(1, 2));
    System.out.println(sum(1, 2));
  }
}