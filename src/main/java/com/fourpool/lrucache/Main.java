package com.fourpool.lrucache;

public class Main {
  public static void main(String[] args) {
    Fibonacci fib = new Fibonacci();
    System.out.println(fib.calculate(5));
    System.out.println();
    System.out.println(fib.cachedCalculate(5));
  }
}
