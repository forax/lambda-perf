package perf;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;

// by convention, the method handle signature are
//   (E element, X result) -> X
//  or
//   (E element) -> void
public class StreamWithMH2<T> implements Stream<T> {
    static abstract class Node {
      final Node previous; 
      
      public Node(Node previous) {
        this.previous = previous;
      }
      
      public abstract MethodHandle chain(MethodHandle mh);
    }
    
    final Node node;
    final Iterable<?> iterable;
    
    private StreamWithMH2(Iterable<?> iterable, Node node) {
      this.iterable = iterable;
      this.node = node;
    }
    
    MethodHandle createPipeline(MethodHandle mh) {
      for(Node n = node; n != null; n = n.previous) {
        mh = node.chain(mh);
      }
      return mh;
    }

    static final MethodHandle EMPTY;
    private static final MethodHandle PREDICATE_TEST;
    private static final MethodHandle MAPPER_MAP;
    private static final MethodHandle BINARYOPERATOR_OPERATE;
    private static final MethodHandle INTBINARYOPERATOR_OPERATE;
    private static final MethodHandle BLOCK_ACCEPT;
    private static final MethodHandle COLLECTION_ADD;
    
    private static final MethodHandle REDUCE_STUB;
    private static final MethodHandle REDUCE_STUB_INT;
    private static final MethodHandle FIND_FIRST_STUB;
    private static final MethodHandle FOR_EACH_STUB;
    static {
      Lookup lookup = MethodHandles.lookup();
      try {
        EMPTY = lookup.findStatic(StreamWithMH2.class, "empty", MethodType.methodType(void.class));
        PREDICATE_TEST = lookup.findVirtual(Predicate.class, "test", MethodType.methodType(boolean.class, Object.class));
        MAPPER_MAP = lookup.findVirtual(Mapper.class, "map", MethodType.methodType(Object.class, Object.class));
        BINARYOPERATOR_OPERATE = lookup.findVirtual(BinaryOperator.class, "operate", MethodType.methodType(Object.class, Object.class, Object.class));
        INTBINARYOPERATOR_OPERATE = lookup.findVirtual(IntBinaryOperator.class, "operate", MethodType.methodType(int.class, int.class, int.class)).
            asType(MethodType.methodType(int.class, IntBinaryOperator.class, Integer.class, int.class)).
            asType(MethodType.methodType(int.class, IntBinaryOperator.class, Object.class, int.class));
        BLOCK_ACCEPT = lookup.findVirtual(Block.class, "accept", MethodType.methodType(void.class, Object.class));
        COLLECTION_ADD = lookup.findVirtual(Collection.class, "add", MethodType.methodType(boolean.class, Object.class)).
            asType(MethodType.methodType(void.class, Collection.class, Object.class));
        
        REDUCE_STUB = lookup.findStatic(StreamWithMH2.class, "reduceStub", MethodType.methodType(Object.class, MethodHandle.class, Iterable.class, Object.class));
        REDUCE_STUB_INT = lookup.findStatic(StreamWithMH2.class, "reduceStubInt", MethodType.methodType(int.class, MethodHandle.class, Iterable.class, int.class));
        FIND_FIRST_STUB = lookup.findStatic(StreamWithMH2.class, "findFirstStub", MethodType.methodType(Object.class, MethodHandle.class, Iterable.class, Object.class));
        FOR_EACH_STUB = lookup.findStatic(StreamWithMH2.class, "forEachStub", MethodType.methodType(void.class, MethodHandle.class, Iterable.class));
        
      } catch (NoSuchMethodException | IllegalAccessException e) {
        throw new AssertionError(e);
      }
    }
    
    @SuppressWarnings("unused")  // used by method handles
    private static void empty() {
      // do nothing, used as fallback branch of predicate for reducer that returns void
    }
    
    static MethodHandle predicateAsMethodHandle(Predicate<?> predicate, MethodType type) {
      //TODO detect SAM proxy that contains a method handle
      return PREDICATE_TEST.bindTo(predicate).asType(MethodType.methodType(boolean.class, type.parameterType(0)));
    }
    
    @Override
    public Stream<T> filter(final Predicate<? super T> predicate) {
      return new StreamWithMH2<>(iterable, new Node(node) {
        @Override
        public MethodHandle chain(MethodHandle mh) {
          MethodType type = mh.type();
          MethodHandle test = predicateAsMethodHandle(predicate, type);
          MethodHandle identity = (type.parameterCount() == 2)? MethodHandles.identity(type.parameterType(1)): EMPTY;
          identity = MethodHandles.dropArguments(identity, 0, type.parameterType(0));
          return MethodHandles.guardWithTest(test, mh, identity);
        }
      });
    }

    static MethodHandle mapperAsMethodHandle(Mapper<?, ?> mapper, MethodType type) {
      //TODO detect SAM proxy that contains a method handle
      MethodHandle map = MAPPER_MAP.bindTo(mapper);
      return map.asType(map.type().changeReturnType(type.parameterType(0)));
    }
    
    @Override
    public <R> Stream<R> map(final Mapper<? super T, ? extends R> mapper) {
      return new StreamWithMH2<>(iterable, new Node(node) {
        @Override
        public MethodHandle chain(MethodHandle mh) {
          MethodType type = mh.type();
          MethodHandle filter = mapperAsMethodHandle(mapper, type);
          return MethodHandles.filterArguments(mh, 0, filter);
        }
      });
    }

    private static RuntimeException rethrow(Throwable t) {
      if (t instanceof RuntimeException) {
        return (RuntimeException)t;
      }
      if (t instanceof Error) {
        throw (Error)t;
      }
      return new UndeclaredThrowableException(t);
    }
    
    private static MethodHandle binaryOperatorAsMethodHandle(BinaryOperator<?> binop) {
      //TODO detect SAM proxy that contains a method handle
      return BINARYOPERATOR_OPERATE.bindTo(binop);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T reduce(T base, BinaryOperator<T> binop) {
      MethodHandle reduce = binaryOperatorAsMethodHandle(binop);
      MethodHandle mh = createPipeline(reduce);
      
      MethodHandle specialized = MethodHandles.insertArguments(REDUCE_STUB, 0, mh, iterable, base);
      try {
        return (T)specialized.invokeExact();
      } catch(Throwable t) {
        throw rethrow(t);
      }
    }
    
    @SuppressWarnings("unused")
    private static Object reduceStub(MethodHandle mh, Iterable<Object> iterable, Object base) throws Throwable {
      Object result = base;
      for(Object o: iterable) {
        result = mh.invokeExact(o, result);
      }
      return result;
    }

    private static MethodHandle intbinaryOperatorAsMethodHandle(IntBinaryOperator binop) {
      //TODO detect SAM proxy that contains a method handle
      return INTBINARYOPERATOR_OPERATE.bindTo(binop);
    }
    
    @Override
    public int reduce(int base, IntBinaryOperator binop) {
      MethodHandle reduce = intbinaryOperatorAsMethodHandle(binop);
      MethodHandle mh = createPipeline(reduce);
      
      MethodHandle specialized = MethodHandles.insertArguments(REDUCE_STUB_INT, 0, mh, iterable, base);
      try {
        return (int)specialized.invokeExact();
      } catch(Throwable t) {
        throw rethrow(t);
      }
    }
    
    @SuppressWarnings("unused")
    private static int reduceStubInt(MethodHandle mh, Iterable<Object> iterable, int base) throws Throwable {
      int result = base;
      for(Object o: iterable) {
        result = (int)mh.invokeExact(o, result);
      }
      return result;
    }
    
    @SuppressWarnings("serial")
    static class ControlFlowException extends Throwable {
      Object value;
      
      ControlFlowException() {
        super(null, null, false, false);
      }
      
      public static ControlFlowException initValue(Object value) {
        ControlFlowException controlFlowException = CACHE.get();
        controlFlowException.value = value;
        return controlFlowException;
      }
      
      Object value() {
        Object value = this.value;
        this.value = null;
        return value;
      }
      
      private static final ThreadLocal<ControlFlowException> CACHE =
          new ThreadLocal<ControlFlowException>() {
          @Override
          protected ControlFlowException initialValue() {
            return new ControlFlowException();
          }
        };
          
      static final MethodHandle INIT_VALUE;
      static {
        try {
          INIT_VALUE = MethodHandles.lookup().findStatic(ControlFlowException.class, "initValue",
            MethodType.methodType(ControlFlowException.class, Object.class));
        } catch(NoSuchMethodException| IllegalAccessException e) {
          throw new AssertionError(e);
        }
      }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T findFirst(T defaultValue) {
      MethodHandle mh = MethodHandles.throwException(void.class, ControlFlowException.class);
      mh = MethodHandles.filterArguments(mh, 0, ControlFlowException.INIT_VALUE);
      mh = createPipeline(mh);
      
      MethodHandle specialized = MethodHandles.insertArguments(FIND_FIRST_STUB, 0, mh, iterable, defaultValue);
      try {
        return (T)specialized.invokeExact();
      } catch(Throwable t) {
        throw rethrow(t);
      }
    }
    
    @SuppressWarnings("unused")
    private static Object findFirstStub(MethodHandle mh, Iterable<Object> iterable, Object defaultValue) throws Throwable {
      try {
        for(Object o: iterable) {
          mh.invokeExact(o);
        }
        return defaultValue;
      } catch(ControlFlowException e) {
        return e.value();
      }
    }
    
    private static MethodHandle blockAsMethodHandle(Block<?> block) {
      //TODO detect SAM proxy that contains a method handle
      return BLOCK_ACCEPT.bindTo(block);
    }
    
    @Override
    public void forEach(Block<? super T> block) {
      MethodHandle mh = blockAsMethodHandle(block);
      mh = createPipeline(mh);
      
      MethodHandle specialized = MethodHandles.insertArguments(FOR_EACH_STUB, 0, mh, iterable);
      try {
        specialized.invokeExact();
      } catch(Throwable t) {
        throw rethrow(t);
      }
    }
    
    @SuppressWarnings("unused")
    private static void forEachStub(MethodHandle mh, Iterable<Object> iterable) throws Throwable {
      for(Object o: iterable) {
        mh.invokeExact(o);
      }
    }

    private static MethodHandle collectionAsMethodHandle(Collection<?> collection) {
      //TODO detect SAM proxy that contains a method handle
      return COLLECTION_ADD.bindTo(collection);
    }
    
    @Override
    public <A extends Collection<? super T>> A into(A target) {
      MethodHandle mh = collectionAsMethodHandle(target);
      mh = createPipeline(mh);
      
      MethodHandle specialized = MethodHandles.insertArguments(FOR_EACH_STUB, 0, mh, iterable);
      try {
        specialized.invokeExact();
      } catch(Throwable t) {
        throw rethrow(t);
      }
      return target;
    }
    
    public static <T> Stream<T> asStream(Iterable<? extends T> iterable) {
      return new StreamWithMH2<>(iterable, null);
    }
  }
