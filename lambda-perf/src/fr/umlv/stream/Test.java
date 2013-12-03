package fr.umlv.stream;

import java.lang.invoke.EscapableStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class Test {
  private static int testStream1(ArrayList<Integer> list) {
    Stream<Integer> stream = EscapableStream.stream(list);
    //Stream<Integer> stream = list.stream();
    int[] value = new int[1];
    stream.filter(x -> x % 1000 != 0).map(x -> x).forEach(x -> value[0]++);
    return value[0];
  }
  
  private static int testStream2(ArrayList<Integer> list) {
    Stream<Integer> stream = EscapableStream.stream(list);
    //Stream<Integer> stream = list.stream();
    int[] value = new int[1];
    stream.filter(x -> x % 1000 != 0).map(x -> x).forEach(x -> value[0]++);
    return value[0];
  }
  
  private static int testStream3(ArrayList<Integer> list) {
    Stream<Integer> stream = EscapableStream.stream(list);
    //Stream<Integer> stream = list.stream();
    int[] value = new int[1];
    stream.filter(x -> x % 1000 != 0).map(x -> x).forEach(x -> value[0]++);
    return value[0];
  }
  
  private static int testStreamIt(ArrayList<Integer> list) {
    Stream<Integer> stream = EscapableStream.stream(list);
    //Stream<Integer> stream = list.stream();
    
    Iterator<Integer> it = stream.filter(x -> x % 1000 != 0).<Integer>map(x -> x).iterator();
    int value = 0;
    while(it.hasNext()) {
      it.next();
      value++;
    }
    return value;
  }
  
  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    for(int i=0; i<1000; i++) {
      list.add(i);
    }
    int sum = 0;
    for(int i=0; i<100_000; i++) {
      //sum += testStream1(list);
      //sum += testStream2(list);
      //sum += testStream3(list);
      sum += testStreamIt(list);
    }
    System.out.println(sum);
  }
}
