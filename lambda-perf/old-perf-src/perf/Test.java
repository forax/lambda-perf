package perf;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import perf.StreamFactory.Impl;


public class Test {

  private static void check(Object result, Object expected, String message) {
    if (!Objects.equals(expected, result)) {
      throw new AssertionError("expect "+expected+" but found "+result+", "+message);
    }
  }
  
  private static void init(StreamFactory factory) {
    Stream<Integer> stream1 = factory.asStream(Arrays.asList(2, 4, 5, 8, 9, 11));
    int result1 = stream1.filter(new Predicate<Integer>() {
      @Override
      public boolean test(Integer i) {
        return i % 2 == 0;
      }
    }).reduce(0, new IntBinaryOperator() {
      @Override
      public int operate(int left, int right) {
        return left + right;
      }
    });
    check(result1, 14, factory.toString());
    
    Stream<String> stream2 = factory.asStream(Arrays.asList("hello", "from", "-init-"));
    final int[] result2 = {0 };
    stream2.map(new Mapper<String, Integer>() {
      @Override
      public Integer map(String s) {
        return s.length();
      }
    }).forEach(new Block<Integer>() {
      @Override
      public void accept(Integer i) {
         result2[0] += i;
      }
    });
    check(result2[0], 15, factory.toString());
    
    Stream<Double> stream3 = factory.asStream(Arrays.asList(1.2, 3.4, Double.NaN, -7.8));
    boolean result3 = stream3.map(new Mapper<Double, Boolean>() {
      @Override
      public Boolean map(Double d) {
        return Double.isNaN(d);
      }
    }).findFirst(true);
    check(result3, false, factory.toString());
    
    AbstractList<Integer> range = new AbstractList<Integer>() {
      @Override
      public Integer get(int index) {
        return index;
      }

      @Override
      public int size() {
        return 10000;
      }
      
    };
    List<Integer> result4 = factory.asStream(range).filter(new Predicate<Integer>() {
      @Override
      public boolean test(Integer i) {
        return i % 17 != 0;
      }
    }).into(new ArrayList<Integer>());
    check(result4.size(), 9411, factory.toString());
  }
  
  private static void testForEach(Stream<Integer> stream) {
    final int[] result = { 0 };
    stream.filter(new Predicate<Integer>() {
      @Override
      public boolean test(Integer i) {
        return i % 3 != 0;
      }
    }).forEach(new Block<Integer>() {
      @Override
      public void accept(Integer i) {
        result[0] += i;
      }
    });
  }
  
  private static void testReduce(Stream<Integer> stream) {
    int result = stream.filter(new Predicate<Integer>() {
      @Override
      public boolean test(Integer i) {
        return i % 3 != 0;
      }
    }).reduce(0, new IntBinaryOperator() {
      @Override
      public int operate(int left, int right) {
        return left + right;
      }
    });
  }
  
  private static void testFindFirst(Stream<Integer> stream) {
    int result = stream.filter(new Predicate<Integer>() {
      @Override
      public boolean test(Integer i) {
        return i != 777;
      }
    }).findFirst(0);
  }
  
  private static void warmup() {
    Random random = new Random(0);
    ArrayList<Integer> list = new ArrayList<>(100_000);
    for(int i=0; i<100_000; i++) {
      list.add(random.nextInt());
    }
    for(Impl impl: Impl.values()) {
      Stream<Integer> stream = impl.asStream(list);
      perfForEach(stream);
      perfReduce(stream);
      perfFindFirst(stream);
    }
  }
  
  private static long perfForEach(Stream<Integer> stream) {
    long start = System.nanoTime();
    testForEach(stream);
    long end = System.nanoTime();
    return end-start;
  }
  
  private static long perfReduce(Stream<Integer> stream) {
    long start = System.nanoTime();
    testReduce(stream);
    long end = System.nanoTime();
    return end-start;
  }
  
  private static long perfFindFirst(Stream<Integer> stream) {
    long start = System.nanoTime();
    testFindFirst(stream);
    long end = System.nanoTime();
    return end-start;
  }
  
  private static void perf() {
    Random random = new Random(0);
    ArrayList<Integer> list = new ArrayList<>(10_000_000);
    for(int i=0; i<10_000_000; i++) {
      list.add(random.nextInt());
    }

    for(Impl impl: Impl.values()) {
      System.out.println("--- " + impl + " ---");
      System.gc();
      
      Stream<Integer> stream = impl.asStream(list);
      for(int i=0; i<20; i++) {
        System.out.println("forEach   " + perfForEach(stream));
        System.out.println("reduce    " + perfReduce(stream));
        System.out.println("findFirst " + perfFindFirst(stream));
      }
    }
  }
  
  public static void main(String[] args) {
    for(Impl impl: Impl.values()) {
      init(impl);
    }
    
    warmup();
    
    perf();
  }

  

}
