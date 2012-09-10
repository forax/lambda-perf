package perf;


import java.util.Collection;

public class StreamWithReducer<T> implements Stream<T> {
  static abstract class Reducer {
    public Object accept(Object element, Object value) {
      throw new UnsupportedOperationException();
    }
    public int accept(Object element, int value) {
      throw new UnsupportedOperationException();
    }
  }
  
  static abstract class Node {
    final Node previous; 
    
    public Node(Node previous) {
      this.previous = previous;
    }
    
    public abstract Reducer createReducer(Reducer reducer);
  }
  
  final Node node;
  final Iterable<?> iterable;
  
  private StreamWithReducer(Iterable<?> iterable, Node node) {
    this.iterable = iterable;
    this.node = node;
  }
  
  Reducer createPipeline(Reducer reducer) {
    for(Node n = node; n != null; n = n.previous) {
      reducer = node.createReducer(reducer);
    }
    return reducer;
  }
  
  @Override
  public Stream<T> filter(final Predicate<? super T> predicate) {
    return new StreamWithReducer<>(iterable, new Node(node) {
      @Override
      public Reducer createReducer(final Reducer reducer) {
        return new Reducer() {
          @Override
          @SuppressWarnings("unchecked")
          public Object accept(Object element, Object value) {
            if (predicate.test((T)element)) {
              return reducer.accept(element, value);
            }
            return value;
          }
          
          @Override
          @SuppressWarnings("unchecked")
          public int accept(Object element, int value) {
            if (predicate.test((T)element)) {
              return reducer.accept(element, value);
            }
            return value;
          }
        };
      }
    });
  }

  @Override
  public <R> Stream<R> map(final Mapper<? super T, ? extends R> mapper) {
    return new StreamWithReducer<>(iterable, new Node(node) {
      @Override
      @SuppressWarnings("unchecked")
      public Reducer createReducer(final Reducer reducer) {
        return new Reducer() {
          @Override
          public Object accept(Object element, Object value) {
            return reducer.accept(mapper.map((T)element), value);
          }
          @Override
          public int accept(Object element, int value) {
            return reducer.accept(mapper.map((T)element), value);
          }
        };
      }
    });
  }

  @Override
  public void forEach(final Block<? super T> block) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      @SuppressWarnings("unchecked")
      public Object accept(Object element, Object value) {
        block.accept((T)element);
        return null;
      }
    });
    
    for(Object element: iterable) {
      reducer.accept(element, null);
    }
  }

  @Override
  public <A extends Collection<? super T>> A into(final A target) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      @SuppressWarnings("unchecked")
      public Object accept(Object element, Object value) {
        target.add((T)element);
        return null;
      }
    });
    
    for(Object element: iterable) {
      reducer.accept(element, null);
    }
    return target;
  }

  
  @Override
  @SuppressWarnings("unchecked")
  public T reduce(T base, final BinaryOperator<T> op) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      //@SuppressWarnings("unchecked")
      public Object accept(Object element, Object value) {
        return op.operate((T)element, (T)value);
      }
    });
    
    Object value = base;
    for(Object element: iterable) {
      value = reducer.accept(element, value);
    }
    return (T)value;
  }
  
  @Override
  public int reduce(int base, final IntBinaryOperator op) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      public int accept(Object element, int value) {
        return op.operate((Integer)element, value);
      }
    });
    
    int value = base;
    for(Object element: iterable) {
      value = reducer.accept(element, value);
    }
    return value;
  }

  @SuppressWarnings("serial")
  static class ControlFlowException extends Error {
    private Object value;
    
    ControlFlowException() {
      super(null, null, false, false);
    }
    
    final Object value() {
      Object value = this.value;
      this.value = null;
      return value;
    }
    
    static ControlFlowException initValue(Object element) {
      ControlFlowException e = CACHE.get();
      Object value = element;
      e.value = value;
      return e;
    }
    
    private static final ThreadLocal<ControlFlowException> CACHE =
        new ThreadLocal<ControlFlowException>() {
          @Override
          protected ControlFlowException initialValue() {
            return new ControlFlowException();
          }
        };
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public T findFirst(T defaultValue) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      public Object accept(Object element, Object value) {
        throw ControlFlowException.initValue(element);
      }
    });
    
    try {
      for(Object element: iterable) {
        reducer.accept(element, null);
      }
      return defaultValue;
    } catch(ControlFlowException e) {
      return (T)e.value();
    }
  }
  
  public static <T> Stream<T> asStream(Iterable<? extends T> iterable) {
    return new StreamWithReducer<>(iterable, null);
  }
}
