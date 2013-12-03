package perf;


import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StreamWithIterable<T> implements Stream<T> {
  final Iterable<? extends T> iterable;
  
  private StreamWithIterable(Iterable<? extends T> iterable) {
    this.iterable = iterable;
  }
  
  static final Object EMPTY = new Object();
  
  @Override
  public Stream<T> filter(final Predicate<? super T> predicate) {
    return new StreamWithIterable<>(new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        final Iterator<? extends T> it = iterable.iterator();
        return new Iterator<T>() {
          private Object next = EMPTY;
          
          @Override
          public boolean hasNext() {
            if (next != EMPTY) {
              return true;
            }
            while (it.hasNext()) {
              T element = it.next();
              if (predicate.test(element)) {
                next = element;
                return true;
              }
            }
            return false;
          }
          
          @Override
          @SuppressWarnings("unchecked")
          public T next() {
            if (!hasNext()) {
              throw new NoSuchElementException();
            }
            Object element = next;
            next = EMPTY;
            return (T)element;
          }

          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    });
  }

  @Override
  public <R> Stream<R> map(final Mapper<? super T, ? extends R> mapper) {
    return new StreamWithIterable<>(new Iterable<R>() {
      @Override
      public Iterator<R> iterator() {
        final Iterator<? extends T> it = iterable.iterator();
        return new Iterator<R>() {
          @Override
          public boolean hasNext() {
            return it.hasNext();
          }
          @Override
          public R next() {
            return mapper.map(it.next());
          }

          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    });
  }

  @Override
  public void forEach(Block<? super T> block) {
    for(T element: iterable) {
      block.accept(element);
    }
  }

  @Override
  public <A extends Collection<? super T>> A into(A target) {
    for(T element: iterable) {
      target.add(element);
    }
    return target;
  }

  @Override
  public T reduce(T base, BinaryOperator<T> op) {
    T value = base;
    for(T element: iterable) {
      value = op.operate(value, element);
    }
    return value;
  }
  
  @Override
  public int reduce(int base, IntBinaryOperator op) {
    int value = base;
    for(T element: iterable) {
      value = op.operate(value, (Integer)element);
    }
    return value;
  }

  @Override
  public T findFirst(T defaultValue) {
    Iterator<? extends T> it = iterable.iterator();
    if (!it.hasNext()) {
      return defaultValue;
    }
    return it.next();
  }
  
  public static <T> Stream<T> asStream(Iterable<? extends T> iterable) {
    return new StreamWithIterable<>(iterable);
  }
}
