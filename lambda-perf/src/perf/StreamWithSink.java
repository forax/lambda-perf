package perf;


import java.util.Collection;

public class StreamWithSink<T> implements Stream<T> {
  static interface Sink {
    public void accept(Object element);
  }
  
  static abstract class Node {
    final Node previous; 
    
    public Node(Node previous) {
      this.previous = previous;
    }
    
    public abstract Sink createSink(Sink sink);
  }
  
  final Node node;
  final Iterable<?> iterable;
  
  private StreamWithSink(Iterable<?> iterable, Node node) {
    this.iterable = iterable;
    this.node = node;
  }
  
  Sink createPipeline(Sink sink) {
    for(Node n = node; n != null; n = n.previous) {
      sink = node.createSink(sink);
    }
    return sink;
  }
  
  @Override
  public Stream<T> filter(final Predicate<? super T> predicate) {
    return new StreamWithSink<>(iterable, new Node(node) {
      @Override
      public Sink createSink(final Sink sink) {
        return new Sink() {
          @Override
          @SuppressWarnings("unchecked")
          public void accept(Object element) {
            if (predicate.test((T)element)) {
              sink.accept(element);
            }
          }
        };
      }
    });
  }

  @Override
  public <R> Stream<R> map(final Mapper<? super T, ? extends R> mapper) {
    return new StreamWithSink<>(iterable, new Node(node) {
      @Override
      @SuppressWarnings("unchecked")
      public Sink createSink(final Sink sink) {
        return new Sink() {
          @Override
          public void accept(Object element) {
            sink.accept(mapper.map((T)element));
          }
        };
      }
    });
  }

  @Override
  public void forEach(final Block<? super T> block) {
    Sink sink = createPipeline(new Sink() {
      @Override
      @SuppressWarnings("unchecked")
      public void accept(Object element) {
        block.accept((T)element);
      }
    });
    
    for(Object element: iterable) {
      sink.accept(element);
    }
  }

  @Override
  public <A extends Collection<? super T>> A into(final A target) {
    Sink sink = createPipeline(new Sink() {
      @Override
      @SuppressWarnings("unchecked")
      public void accept(Object element) {
        target.add((T)element);
      }
    });
    
    for(Object element: iterable) {
      sink.accept(element);
    }
    return target;
  }

  @Override
  public T reduce(final T base, final BinaryOperator<T> op) {
    class Reducer implements Sink {
      T value = base;
      
      @Override
      @SuppressWarnings("unchecked")
      public void accept(Object element) {
        value = op.operate((T)element, value);
      }
    }
  
    Reducer reducer = new Reducer();
    Sink sink = createPipeline(reducer);
    for(Object element: iterable) {
      sink.accept(element);
    }
    return reducer.value;
  }
  
  @Override
  public int reduce(final int base, final IntBinaryOperator op) {
    class Reducer implements Sink {
      int value = base;
      
      @Override
      public void accept(Object element) {
        value = op.operate((Integer)element, value);
      }
    }
  
    Reducer reducer = new Reducer();
    Sink sink = createPipeline(reducer);
    for(Object element: iterable) {
      sink.accept(element);
    }
    return reducer.value;
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
    Sink sink = createPipeline(new Sink() {
      @Override
      public void accept(Object element) {
        throw ControlFlowException.initValue(element);
      }
    });
    
    try {
      for(Object element: iterable) {
        sink.accept(element);
      }
      return defaultValue;
    } catch(ControlFlowException e) {
      return (T)e.value();
    }
  }
  
  public static <T> Stream<T> asStream(Iterable<? extends T> iterable) {
    return new StreamWithSink<>(iterable, null);
  }
}
