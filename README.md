LruCache
========
Annotation-triggered memoization for pure Java functions via AspectJ inspired by Python's [lru_cache](https://docs.python.org/3/library/functools.html#functools.lru_cache) decorator.

Memoization is a common optimization technique for caching previously computed values. Methods annotated with `@LruCache` will have this automatically done for them, using the method's parameters as the cache key in an LRU cache. By default, the cache has a max capacity of 1024, after which LRU eviction will occur.

Usage
-----
```java
/** Calculates the nth fibonacci number. */
class Fibonacci {
  /** Vanilla recursive fibonacci number generator. */
  int calculate(int n) {
    System.out.println("Invoked calculate(" + n + ")");
    if (n == 1) return 1;
    if (n == 2) return 1;
    return calculate(n - 1) + calculate(n - 2);
  }

  /** Annotated fibonacci number generator with memoization. */
  @LruCache
  int cachedCalculate(int n) {
    System.out.println("Invoked cachedCalculate(" + n + ")");
    if (n == 1) return 1;
    if (n == 2) return 1;
    return cachedCalculate(n - 1) + cachedCalculate(n - 2);
  }
}
```

```java
Fibonacci fib = new Fibonacci();
// Un-annotated version.
System.out.println("7th fibonacci number: " + fib.calculate(7));
System.out.println();

// Annotated version.
System.out.println("7th fibonacci number: " + fib.cachedCalculate(7));
```

```
Invoked calculate(7)
Invoked calculate(6)
Invoked calculate(5)
Invoked calculate(4)
Invoked calculate(3)
Invoked calculate(2)
Invoked calculate(1)
Invoked calculate(2)
Invoked calculate(3)
Invoked calculate(2)
Invoked calculate(1)
Invoked calculate(4)
Invoked calculate(3)
Invoked calculate(2)
Invoked calculate(1)
Invoked calculate(2)
Invoked calculate(5)
Invoked calculate(4)
Invoked calculate(3)
Invoked calculate(2)
Invoked calculate(1)
Invoked calculate(2)
Invoked calculate(3)
Invoked calculate(2)
Invoked calculate(1)
7th fibonacci number: 13

Invoked cachedCalculate(7)
Invoked cachedCalculate(6)
Invoked cachedCalculate(5)
Invoked cachedCalculate(4)
Invoked cachedCalculate(3)
Invoked cachedCalculate(2)
Invoked cachedCalculate(1)
7th fibonacci number: 13
```

In the above example, both the `calculate` and `cachedCalculate` methods return the correct result of `13`. However, the annotated `cachedCalculate` method does not re-compute previously computed results, and thus prevents itself from performing unnecessary computations.

Cache Stats
-----------
To inspect the cache, and cache stats such as the number of hits or misses, provide a `key` with the `@LruCache` annotation, and then fetch the `CacheStats` object associated with that `key`.

```java
class Fibonacci {
  /** Annotated fibonacci number generator with memoization. */
  @LruCache(key = "fib")
  int cachedCalculate(int n) {
    if (n == 1) return 1;
    if (n == 2) return 1;
    return cachedCalculate(n - 1) + cachedCalculate(n - 2);
  }
}
```

```java
var fib = new Fibonacci();
System.out.println("7th fibonacci number: " + fib.cachedCalculate(7));
var cacheStats = Caches.CACHE_STATS.get("fib");
System.out.println("Cache hits: " + cacheStats.hits);
System.out.println("Cache misses: " + cacheStats.misses);
```

```
7th fibonacci number: 13
Cache hits: 4
Cache misses: 7
```

Install
-------
#### Using Gradle
```groovy
buildscript {
  repositories {
    maven {
      url "https://maven.eveoh.nl/content/repositories/releases"
    }
  }

  dependencies {
    classpath 'nl.eveoh:gradle-aspectj:1.6'
  }
}

project.ext {
  aspectjVersion = '1.9.2'
}

apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'aspectj'

repositories {
  mavenCentral()
  maven { url 'https://jitpack.io' }
}

dependencies {
  compile "com.github.matthewmichihara.lrucache:lrucache:master-SNAPSHOT"
  aspectpath "com.github.matthewmichihara.lrucache:lrucache:master-SNAPSHOT"
}
```

#### Using Maven
See [sample project](/lrucache/sample-maven-jitpack).


Build
-----
```
> java -version
java version "11.0.1" 2018-10-16 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.1+13-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.1+13-LTS, mixed mode)
```
```
mvn clean install
cd sample
mvn exec:java
```
