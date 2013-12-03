package serialization;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class Test2 {
  static class Proxy implements Runnable {
    private final int[] array;
    private final int index;
    
    public Proxy(int[] array, int index) {
      this.array = array;
      this.index = index;
    }

    @Override
    public void run() {
      array[index] = index;
    }
  }
  
  static class LambdaSerializationInfo {
    // put fields here
  }
  
  static class ProxySer implements Runnable {
    private final LambdaSerializationInfo info;
    private final int[] array;
    private final int index;
    
    public ProxySer(LambdaSerializationInfo info, int[] array, int index) {
      this.info = info;
      this.array = array;
      this.index = index;
    }

    @Override
    public void run() {
      array[index] = index;
    }
  }
  
  private static final MethodHandle MH_WITHOUT_SER;
  private static final MethodHandle MH_WITH_SER;
  static {
    Lookup lookup = MethodHandles.lookup();
    
    try {
      MethodHandle mh = lookup.findConstructor(Proxy.class, MethodType.methodType(void.class, int[].class, int.class));
      MH_WITHOUT_SER = mh.asType(mh.type().changeReturnType(Runnable.class));
      
      mh = lookup.findConstructor(ProxySer.class, MethodType.methodType(void.class, LambdaSerializationInfo.class, int[].class, int.class));
      mh = mh.bindTo(new LambdaSerializationInfo());
      MH_WITH_SER = mh.asType(mh.type().changeReturnType(Runnable.class));
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }
  
  private static Runnable r1;
  
  private static void createWithoutSerialization() throws Throwable {
    int[] array = new int[10_000_000];
    for(int i=0; i<array.length; i++) {
      r1 = (Runnable)MH_WITHOUT_SER.invokeExact(array, i);
    }
  }
  
  private static Runnable r2;
  
  private static void createWithSerialization() throws Throwable {
    int[] array = new int[10_000_000];
    for(int i=0; i<array.length; i++) {
      r2 = (Runnable)MH_WITH_SER.invokeExact(array, i);
    }
  }
  
  private static void testWithoutSerialization() throws Throwable {
    long start = System.nanoTime();
    createWithoutSerialization();
    long end = System.nanoTime();
    System.out.println(end -start);
  }
  
  private static void testWithSerialization() throws Throwable {
    long start = System.nanoTime();
    createWithSerialization();
    long end = System.nanoTime();
    System.out.println(end -start);
  }
  
  public static void main(String[] args) throws Throwable {
    //testWithoutSerialization();
    testWithSerialization();
  }
  
}
