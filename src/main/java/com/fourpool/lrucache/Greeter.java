package com.fourpool.lrucache;

class Greeter {
  @LruCache
  String getGreeting(String name) {
    return "Hello " + name;
  }

  @LruCache
  int calculate(int a, int b) {
    return a + b;
  }
}
