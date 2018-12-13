LruCache
========
Annotation-triggered memoization for pure Java functions via AspectJ.

Usage
-----
```java
class Fibonacci {
  @LruCache
  int calculate(int n) {
    System.out.println("calculate(" + n + ")");
    if (n == 1) return 1;
    if (n == 2) return 1;
    return calculate(n - 1) + calculate(n - 2);
  }
```

```java
public class Main {
  public static void main(String[] args) {
    Fibonacci fib = new Fibonacci();
    System.out.println(fib.calculate(5));
  }
}
```


