package perf;


import java.util.Collection;

public class StreamWithAdhocImpl<T> implements Stream<T> {
  static abstract class Reducer {
    public void protoSink(Object element) {
      throw new UnsupportedOperationException();
    }
    
    public Object protoFind(Object element) {
      throw new UnsupportedOperationException();
    }
    
    public Object protoReduce(Object element, Object value) {
      throw new UnsupportedOperationException();
    }
    public int protoReduce(Object element, int value) {
      throw new UnsupportedOperationException();
    }
  }
  
  static final Object NONE = new Object();
  
  static abstract class Node {
    final Node previous; 
    
    public Node(Node previous) {
      this.previous = previous;
    }
    
    public abstract Reducer createReducer(Reducer reducer);
  }
  
  final Node node;
  final Iterable<?> iterable;
  
  private StreamWithAdhocImpl(Iterable<?> iterable, Node node) {
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
    return new StreamWithAdhocImpl<>(iterable, new Node(node) {
      @Override
      @SuppressWarnings("unchecked")
      public Reducer createReducer(final Reducer reducer) {
        return new Reducer() {
          @Override
          public void protoSink(Object element) {
            if (predicate.test((T)element)) {
              reducer.protoSink(element);
            }
          }
          
          @Override
          public Object protoFind(Object element) {
            if (predicate.test((T)element)) {
              return reducer.protoFind(element);
            }
            return NONE;
          }
          
          @Override
          public Object protoReduce(Object element, Object value) {
            if (predicate.test((T)element)) {
              return reducer.protoReduce(element, value);
            }
            return value;
          }
          
          @Override
          public int protoReduce(Object element, int value) {
            if (predicate.test((T)element)) {
              return reducer.protoReduce(element, value);
            }
            return value;
          }
        };
      }
    });
  }

  @Override
  public <R> Stream<R> map(final Mapper<? super T, ? extends R> mapper) {
    return new StreamWithAdhocImpl<>(iterable, new Node(node) {
      @Override
      @SuppressWarnings("unchecked")
      public Reducer createReducer(final Reducer reducer) {
        return new Reducer() {
          @Override
          public void protoSink(Object element) {
            reducer.protoSink(mapper.map((T)element));
          }
          @Override
          public Object protoFind(Object element) {
            return reducer.protoFind(mapper.map((T)element));
          }
          @Override
          public Object protoReduce(Object element, Object value) {
            return reducer.protoReduce(mapper.map((T)element), value);
          }
          @Override
          public int protoReduce(Object element, int value) {
            return reducer.protoReduce(mapper.map((T)element), value);
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
      public void protoSink(Object element) {
        block.accept((T)element);
      }
    });
    
    for(Object element: iterable) {
      reducer.protoSink(element);
    }
  }

  @Override
  public <A extends Collection<? super T>> A into(final A target) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      @SuppressWarnings("unchecked")
      public void protoSink(Object element) {
        target.add((T)element);
      }
    });
    
    for(Object element: iterable) {
      reducer.protoSink(element);
    }
    return target;
  }

  
  @Override
  @SuppressWarnings("unchecked")
  public T reduce(T base, final BinaryOperator<T> op) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      public Object protoReduce(Object element, Object value) {
        return op.operate((T)element, (T)value);
      }
    });
    
    Object value = base;
    for(Object element: iterable) {
      value = reducer.protoReduce(element, value);
    }
    return (T)value;
  }
  
  @Override
  public int reduce(int base, final IntBinaryOperator op) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      public int protoReduce(Object element, int value) {
        return op.operate((Integer)element, value);
      }
    });
    
    int value = base;
    for(Object element: iterable) {
      value = reducer.protoReduce(element, value);
    }
    return value;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public T findFirst(T defaultValue) {
    Reducer reducer = createPipeline(new Reducer() {
      @Override
      public Object protoFind(Object element) {
        return element;
      }
    });

    for(Object element: iterable) {
      Object value = reducer.protoFind(element);
      if (value != NONE) {
        return (T)value;
      }
    }
    return defaultValue;
  }
  
  public static <T> Stream<T> asStream(Iterable<? extends T> iterable) {
    return new StreamWithAdhocImpl<>(iterable, null);
  }
}
