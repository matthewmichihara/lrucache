package com.fourpool.lrucache.samplemavenjitpack;

import com.fourpool.lrucache.LruCache;

public class Main {
  static class Summer {
    @LruCache
    int sum(int a, int b) {
      System.out.println("sum called");
      return a + b;
    }
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
    Summer summer = new Summer();
    System.out.println(summer.sum(1, 2));
    System.out.println(summer.sum(1, 2));
    System.out.println(summer.sum(1, 2));
    System.out.println(summer.sum(1, 2));
    System.out.println(summer.sum(1, 2));
  }
}