package fr.umlv.stream;

import java.lang.invoke.EscapableStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

// -Xbootclasspath/p:classes -Xbatch -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly
public class ForEachTest {
  private static int testStream(ArrayList<Integer> list) {
    Stream<Integer> stream = EscapableStream.stream(list);
    //Stream<Integer> stream = list.stream();
    int[] value = new int[1];
    stream.filter(x -> x % 1000 != 0).map(x -> x).forEach(x -> value[0]++);
    return value[0];
  }
  
  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    for(int i=0; i<1000; i++) {
      list.add(i);
    }
    int sum = 0;
    for(int i=0; i<100_000; i++) {
      sum += testStream(list);
    }
    System.out.println(sum);
  }
}
