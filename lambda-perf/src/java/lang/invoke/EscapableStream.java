package java.lang.invoke;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class EscapableStream<T> implements Stream<T> {
  private final @Stable Iterable<?> iterable;
  private final @Stable int op0;
  private final @Stable int op1;
  private final @Stable int op2;
  private final @Stable int op3;
  private final @Stable int op4;
  private final @Stable int op5;
  private final @Stable int op6;
  private final @Stable int op7;
  private final @Stable Object lambda0;
  private final @Stable Object lambda1;
  private final @Stable Object lambda2;
  private final @Stable Object lambda3;
  private final @Stable Object lambda4;
  private final @Stable Object lambda5;
  private final @Stable Object lambda6;
  private final @Stable Object lambda7;
  
  private static final int OP_NONE = 0;
  private static final int OP_FILTER = 1;
  private static final int OP_MAP = 2;
  private static final int OP_PEEK = 3;
  private static final int OP_FLATMAP = 15;
  
  @ForceInline
  private EscapableStream(Iterable<?> iterable,
      int op0, int op1, int op2, int op3, int op4, int op5, int op6, int op7,
      Object lambda0, Object lambda1, Object lambda2, Object lambda3, Object lambda4, Object lambda5, Object lambda6, Object lambda7) {
    this.iterable = iterable;
    this.op0 = op0;
    this.op1 = op1;
    this.op2 = op2;
    this.op3 = op3;
    this.op4 = op4;
    this.op5 = op5;
    this.op6 = op6;
    this.op7 = op7;
    this.lambda0 = lambda0;
    this.lambda1 = lambda1;
    this.lambda2 = lambda2;
    this.lambda3 = lambda3;
    this.lambda4 = lambda4;
    this.lambda5 = lambda5;
    this.lambda6 = lambda6;
    this.lambda7 = lambda7;
  }

  @ForceInline
  public static <T> Stream<T> stream(Iterable<? extends T> iterable) {
    return new EscapableStream<>(iterable,
        0, 0, 0, 0, 0, 0, 0, 0,
        null, null, null, null, null, null, null, null);
  }
  
  @ForceInline
  private static final void throwUOE() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  @ForceInline
  public Stream<T> filter(Predicate<? super T> predicate) {
    int op = OP_FILTER;
    Object lambda = predicate;
    int op0 = this.op0;
    Object lambda0 = this.lambda0;
    int op1 = this.op1;
    Object lambda1 = this.lambda1;
    int op2 = this.op2;
    Object lambda2 = this.lambda2;
    int op3 = this.op3;
    Object lambda3 = this.lambda3;
    int op4 = this.op4;
    Object lambda4 = this.lambda4;
    int op5 = this.op5;
    Object lambda5 = this.lambda5;
    int op6 = this.op6;
    Object lambda6 = this.lambda6;
    int op7 = this.op7;
    Object lambda7 = this.lambda7;
    if (op0 == 0) { op0 = op; lambda0 = lambda; } else
      if (op1 == 0) { op1 = op; lambda1 = lambda; } else
        if (op2 == 0) { op2 = op; lambda2 = lambda; } else
          if (op3 == 0) { op3 = op; lambda3 = lambda; } else
            if (op4 == 0) { op4 = op; lambda4 = lambda; } else
              if (op5 == 0) { op5 = op; lambda5 = lambda; } else
                if (op6 == 0) { op6 = op; lambda6 = lambda; } else
                  if (op7 == 0) { op7 = op; lambda7 = lambda; } else
                    throwUOE();
    return new EscapableStream<>(iterable,
        op0, op1, op2, op3, op4, op5, op6, op7,
        lambda0, lambda1, lambda2, lambda3, lambda4, lambda5, lambda6, lambda7);
  }

  @Override
  @ForceInline
  public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    int op = OP_MAP;
    Object lambda = mapper;
    int op0 = this.op0;
    Object lambda0 = this.lambda0;
    int op1 = this.op1;
    Object lambda1 = this.lambda1;
    int op2 = this.op2;
    Object lambda2 = this.lambda2;
    int op3 = this.op3;
    Object lambda3 = this.lambda3;
    int op4 = this.op4;
    Object lambda4 = this.lambda4;
    int op5 = this.op5;
    Object lambda5 = this.lambda5;
    int op6 = this.op6;
    Object lambda6 = this.lambda6;
    int op7 = this.op7;
    Object lambda7 = this.lambda7;
    if (op0 == 0) { op0 = op; lambda0 = lambda; } else
      if (op1 == 0) { op1 = op; lambda1 = lambda; } else
        if (op2 == 0) { op2 = op; lambda2 = lambda; } else
          if (op3 == 0) { op3 = op; lambda3 = lambda; } else
            if (op4 == 0) { op4 = op; lambda4 = lambda; } else
              if (op5 == 0) { op5 = op; lambda5 = lambda; } else
                if (op6 == 0) { op6 = op; lambda6 = lambda; } else
                  if (op7 == 0) { op7 = op; lambda7 = lambda; } else
                    throwUOE();
    return new EscapableStream<>(iterable,
        op0, op1, op2, op3, op4, op5, op6, op7,
        lambda0, lambda1, lambda2, lambda3, lambda4, lambda5, lambda6, lambda7);
  }
  
  @Override
  @ForceInline
  public Stream<T> peek(Consumer<? super T> action) {
    int op = OP_PEEK;
    Object lambda = action;
    int op0 = this.op0;
    Object lambda0 = this.lambda0;
    int op1 = this.op1;
    Object lambda1 = this.lambda1;
    int op2 = this.op2;
    Object lambda2 = this.lambda2;
    int op3 = this.op3;
    Object lambda3 = this.lambda3;
    int op4 = this.op4;
    Object lambda4 = this.lambda4;
    int op5 = this.op5;
    Object lambda5 = this.lambda5;
    int op6 = this.op6;
    Object lambda6 = this.lambda6;
    int op7 = this.op7;
    Object lambda7 = this.lambda7;
    if (op0 == 0) { op0 = op; lambda0 = lambda; } else
      if (op1 == 0) { op1 = op; lambda1 = lambda; } else
        if (op2 == 0) { op2 = op; lambda2 = lambda; } else
          if (op3 == 0) { op3 = op; lambda3 = lambda; } else
            if (op4 == 0) { op4 = op; lambda4 = lambda; } else
              if (op5 == 0) { op5 = op; lambda5 = lambda; } else
                if (op6 == 0) { op6 = op; lambda6 = lambda; } else
                  if (op7 == 0) { op7 = op; lambda7 = lambda; } else
                    throwUOE();
    return new EscapableStream<>(iterable,
        op0, op1, op2, op3, op4, op5, op6, op7,
        lambda0, lambda1, lambda2, lambda3, lambda4, lambda5, lambda6, lambda7);
  }
  
  @Override
  @ForceInline
  public <R> Stream<R> flatMap(
      Function<? super T, ? extends Stream<? extends R>> mapper) {
    int op = OP_FLATMAP;
    Object lambda = mapper;
    int op0 = this.op0;
    Object lambda0 = this.lambda0;
    int op1 = this.op1;
    Object lambda1 = this.lambda1;
    int op2 = this.op2;
    Object lambda2 = this.lambda2;
    int op3 = this.op3;
    Object lambda3 = this.lambda3;
    int op4 = this.op4;
    Object lambda4 = this.lambda4;
    int op5 = this.op5;
    Object lambda5 = this.lambda5;
    int op6 = this.op6;
    Object lambda6 = this.lambda6;
    int op7 = this.op7;
    Object lambda7 = this.lambda7;
    if (op0 == 0) { op0 = op; lambda0 = lambda; } else
      if (op1 == 0) { op1 = op; lambda1 = lambda; } else
        if (op2 == 0) { op2 = op; lambda2 = lambda; } else
          if (op3 == 0) { op3 = op; lambda3 = lambda; } else
            if (op4 == 0) { op4 = op; lambda4 = lambda; } else
              if (op5 == 0) { op5 = op; lambda5 = lambda; } else
                if (op6 == 0) { op6 = op; lambda6 = lambda; } else
                  if (op7 == 0) { op7 = op; lambda7 = lambda; } else
                    throwUOE();
    return new EscapableStream<>(iterable,
        op0, op1, op2, op3, op4, op5, op6, op7,
        lambda0, lambda1, lambda2, lambda3, lambda4, lambda5, lambda6, lambda7);
  }
  
  @Override
  @ForceInline
  @SuppressWarnings("unchecked")
  public void forEach(Consumer<? super T> action) {
    for(Object o: iterable) {
      if (op0 == OP_FILTER && !((Predicate<Object>)lambda0).test(o)) continue;
      if (op0 == OP_MAP) o = ((Function<Object, Object>)lambda0).apply(o); else
      if (op0 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it0 = (op0 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda0).apply(o).iterator(): null;
          it0 == null || it0.hasNext();) { if (it0 != null) o = it0.next();
      
      if (op1 == OP_FILTER && !((Predicate<Object>)lambda1).test(o)) continue;
      if (op1 == OP_MAP) o = ((Function<Object, Object>)lambda1).apply(o); else
      if (op1 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it1 = (op1 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda1).apply(o).iterator(): null;
          it1 == null || it1.hasNext();) { if (it1 != null) o = it1.next();
      
      if (op2 == OP_FILTER && !((Predicate<Object>)lambda2).test(o)) continue;
      if (op2 == OP_MAP) o = ((Function<Object, Object>)lambda2).apply(o); else
      if (op2 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it2 = (op2 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda2).apply(o).iterator(): null;
          it2 == null || it2.hasNext();) { if (it2 != null) o = it2.next();
      
      if (op3 == OP_FILTER && !((Predicate<Object>)lambda3).test(o)) continue;
      if (op3 == OP_MAP) o = ((Function<Object, Object>)lambda3).apply(o); else
      if (op3 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it3 = (op3 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda3).apply(o).iterator(): null;
         it3 == null || it3.hasNext();) { if (it3 != null) o = it3.next();
      
      if (op4 == OP_FILTER && !((Predicate<Object>)lambda4).test(o)) continue;
      if (op4 == OP_MAP) o = ((Function<Object, Object>)lambda4).apply(o); else
      if (op4 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it4 = (op4 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda4).apply(o).iterator(): null;
          it4 == null || it4.hasNext();) { if (it4 != null) o = it4.next();
      
      if (op5 == OP_FILTER && !((Predicate<Object>)lambda5).test(o)) continue;
      if (op5 == OP_MAP) o = ((Function<Object, Object>)lambda5).apply(o); else
      if (op5 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it5 = (op5 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda5).apply(o).iterator(): null;
          it5 == null || it5.hasNext();) { if (it5 != null) o = it5.next();
      
      if (op6 == OP_FILTER && !((Predicate<Object>)lambda6).test(o)) continue;
      if (op6 == OP_MAP) o = ((Function<Object, Object>)lambda6).apply(o); else
      if (op6 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it6 = (op6 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda6).apply(o).iterator(): null;
          it6 == null || it6.hasNext();) { if (it6 != null) o = it6.next();
      
      if (op7 == OP_FILTER && !((Predicate<Object>)lambda7).test(o)) continue;
      if (op7 == OP_MAP) o = ((Function<Object, Object>)lambda7).apply(o); else
      if (op7 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o);
      for(Iterator<?> it7 = (op7 == OP_FLATMAP)? ((Function<Object,Stream<?>>)lambda7).apply(o).iterator(): null;
          it7 == null || it7.hasNext();) { if (it7 != null) o = it7.next();
      
      action.accept((T)o);
      
      if (it7 == null) break; }
      if (it6 == null) break; }
      if (it5 == null) break; }
      if (it4 == null) break; }
      if (it3 == null) break; }
      if (it2 == null) break; }
      if (it1 == null) break; }
      if (it0 == null) break; }
    }
  }
  
  static final Object BLANK = new Object();
  
  static class StreamIterator<T> implements Iterator<T> {
    private final @Stable Iterator<?> iterator;
    private final @Stable int op0;
    private final @Stable int op1;
    private final @Stable int op2;
    private final @Stable int op3;
    private final @Stable int op4;
    private final @Stable int op5;
    private final @Stable int op6;
    private final @Stable int op7;
    private final @Stable Object lambda0;
    private final @Stable Object lambda1;
    private final @Stable Object lambda2;
    private final @Stable Object lambda3;
    private final @Stable Object lambda4;
    private final @Stable Object lambda5;
    private final @Stable Object lambda6;
    private final @Stable Object lambda7;
    private Iterator<Object> it0;
    private Iterator<Object> it1;
    private Iterator<Object> it2;
    private Iterator<Object> it3;
    private Iterator<Object> it4;
    private Iterator<Object> it5;
    private Iterator<Object> it6;
    private Iterator<Object> it7;
    private Object current = BLANK;
    
    @ForceInline
    StreamIterator(Iterator<?> iterator,
        int op0, int op1, int op2, int op3, int op4, int op5, int op6, int op7,
        Object lambda0, Object lambda1, Object lambda2, Object lambda3, Object lambda4, Object lambda5, Object lambda6, Object lambda7) {
      this.iterator = iterator;
      this.op0 = op0;
      this.op1 = op1;
      this.op2 = op2;
      this.op3 = op3;
      this.op4 = op4;
      this.op5 = op5;
      this.op6 = op6;
      this.op7 = op7;
      this.lambda0 = lambda0;
      this.lambda1 = lambda1;
      this.lambda2 = lambda2;
      this.lambda3 = lambda3;
      this.lambda4 = lambda4;
      this.lambda5 = lambda5;
      this.lambda6 = lambda6;
      this.lambda7 = lambda7;
    }
    
    @Override
    @ForceInline
    public boolean hasNext() {
      if (current != BLANK) {
        return true;
      }
      for(;;) {
        if (op7 == OP_FLATMAP && it7 != null && it7.hasNext()) {
          Object current  = it7.next();
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op6 == OP_FLATMAP && it6 != null && it6.hasNext()) {
          Object current = advance7(it6.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op5 == OP_FLATMAP && it5 != null && it5.hasNext()) {
          Object current = advance6(it5.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op4 == OP_FLATMAP && it4 != null && it4.hasNext()) {
          Object current = advance5(it4.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op3 == OP_FLATMAP && it3 != null && it3.hasNext()) {
          Object current = advance4(it3.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op2 == OP_FLATMAP && it2 != null && it2.hasNext()) {
          Object current = advance3(it2.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op1 == OP_FLATMAP && it1 != null && it1.hasNext()) {
          Object current = advance2(it1.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (op0 == OP_FLATMAP && it0 != null && it0.hasNext()) {
          Object current = advance1(it0.next());
          if (current != BLANK) { this.current = current; return true; }
        }
        if (!iterator.hasNext()) {
          return false;
        }
        Object current = advance0(iterator.next()); 
        if (current != BLANK) { this.current = current; return true; }
      }
    }
    
    @DontInline
    private boolean slowHasNext() {
      return hasNext();
    }
    
    @Override
    @ForceInline
    @SuppressWarnings("unchecked")
    public T next() {
      if (current == BLANK && !slowHasNext()) {
        throw new NoSuchElementException();
      }
      Object o = current;
      current = BLANK;
      return (T)o;
    }
    
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance0(Object o) {
      if (op0 == OP_FILTER && !((Predicate<Object>)lambda0).test(o)) return BLANK;
      if (op0 == OP_MAP) o = ((Function<Object, Object>)lambda0).apply(o); else
      if (op0 == OP_PEEK) ((Consumer<Object>)lambda0).accept(o); else
      if (op0 == OP_FLATMAP) {
        if (it0 == null) it0 = ((Function<Object,Stream<Object>>)lambda0).apply(o).iterator();
        if (!it0.hasNext()) { it0 = null; return BLANK; }
        o = it0.next();
      }
      return advance1(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance1(Object o) {
      if (op1 == OP_FILTER && !((Predicate<Object>)lambda1).test(o)) return BLANK;
      if (op1 == OP_MAP) o = ((Function<Object, Object>)lambda1).apply(o); else
      if (op1 == OP_PEEK) ((Consumer<Object>)lambda1).accept(o); else
      if (op1 == OP_FLATMAP) {
        if (it1 == null) it1 = ((Function<Object,Stream<Object>>)lambda1).apply(o).iterator();
        if (!it1.hasNext()) { it1 = null; return BLANK; }
        o = it1.next();
      }
      return advance2(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance2(Object o) {
      if (op2 == OP_FILTER && !((Predicate<Object>)lambda2).test(o)) return BLANK;
      if (op2 == OP_MAP) o = ((Function<Object, Object>)lambda2).apply(o); else
      if (op2 == OP_PEEK) ((Consumer<Object>)lambda2).accept(o); else
      if (op2 == OP_FLATMAP) {
        if (it2 == null) it2 = ((Function<Object,Stream<Object>>)lambda2).apply(o).iterator();
        if (!it2.hasNext()) { it2 = null; return BLANK; }
        o = it2.next();
      }
      return advance3(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance3(Object o) {
      if (op3 == OP_FILTER && !((Predicate<Object>)lambda3).test(o)) return BLANK;
      if (op3 == OP_MAP) o = ((Function<Object, Object>)lambda3).apply(o); else
      if (op3 == OP_PEEK) ((Consumer<Object>)lambda3).accept(o); else
      if (op3 == OP_FLATMAP) {
        if (it3 == null) it3 = ((Function<Object,Stream<Object>>)lambda3).apply(o).iterator();
        if (!it3.hasNext()) { it3 = null; return BLANK; }
        o = it3.next();
      }
      return advance4(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance4(Object o) {
      if (op4 == OP_FILTER && !((Predicate<Object>)lambda4).test(o)) return BLANK;
      if (op4 == OP_MAP) o = ((Function<Object, Object>)lambda4).apply(o); else
      if (op4 == OP_PEEK) ((Consumer<Object>)lambda4).accept(o); else
      if (op4 == OP_FLATMAP) {
        if (it4 == null) it4 = ((Function<Object,Stream<Object>>)lambda4).apply(o).iterator();
        if (!it4.hasNext()) { it4 = null; return BLANK; }
        o = it4.next();
      }
      return advance5(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance5(Object o) {
      if (op5 == OP_FILTER && !((Predicate<Object>)lambda5).test(o)) return BLANK;
      if (op5 == OP_MAP) o = ((Function<Object, Object>)lambda5).apply(o); else
      if (op5 == OP_PEEK) ((Consumer<Object>)lambda5).accept(o); else
      if (op5 == OP_FLATMAP) {
        if (it5 == null) it5 = ((Function<Object,Stream<Object>>)lambda5).apply(o).iterator();
        if (!it5.hasNext()) { it5 = null; return BLANK; }
        o = it5.next();
      }
      return advance6(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance6(Object o) {
      if (op6 == OP_FILTER && !((Predicate<Object>)lambda6).test(o)) return BLANK;
      if (op6 == OP_MAP) o = ((Function<Object, Object>)lambda6).apply(o); else
      if (op6 == OP_PEEK) ((Consumer<Object>)lambda6).accept(o); else
      if (op6 == OP_FLATMAP) {
        if (it6 == null) it6 = ((Function<Object,Stream<Object>>)lambda6).apply(o).iterator();
        if (!it6.hasNext()) { it6 = null; return BLANK; }
        o = it6.next();
      }
      return advance7(o);
    }
    @Hidden
    @ForceInline
    @SuppressWarnings("unchecked")
    private Object advance7(Object o) {
      if (op7 == OP_FILTER && !((Predicate<Object>)lambda7).test(o)) return BLANK;
      if (op7 == OP_MAP) o = ((Function<Object, Object>)lambda7).apply(o); else
      if (op7 == OP_PEEK) ((Consumer<Object>)lambda7).accept(o); else
      if (op7 == OP_FLATMAP) {
        if (it7 == null) it7 = ((Function<Object,Stream<Object>>)lambda7).apply(o).iterator();
        if (!it7.hasNext()) { it7 = null; return BLANK; }
        return it7.next();
      }
      return o;
    }
  }
  
  
  @Override
  @ForceInline
  @SuppressWarnings("unchecked")
  public Iterator<T> iterator() {
    if (op0 == OP_NONE) {  // this shortcut is important for stream.flatMap(list.stream()).forEach(...)
      return (Iterator<T>)iterable.iterator();
    }
    return new StreamIterator<>(iterable.iterator(),
        op0, op1, op2, op3, op4, op5, op6, op7,
        lambda0, lambda1, lambda2, lambda3, lambda4, lambda5, lambda6, lambda7);
  } 
  
  

  @Override
  public Spliterator<T> spliterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isParallel() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> sequential() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> parallel() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> unordered() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> onClose(Runnable closeHandler) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void close() {
    throw new UnsupportedOperationException();
  }

  @Override
  public IntStream mapToInt(ToIntFunction<? super T> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LongStream mapToLong(ToLongFunction<? super T> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LongStream flatMapToLong(
      Function<? super T, ? extends LongStream> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public DoubleStream flatMapToDouble(
      Function<? super T, ? extends DoubleStream> mapper) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> distinct() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> sorted() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> sorted(Comparator<? super T> comparator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> limit(long maxSize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Stream<T> skip(long n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEachOrdered(Consumer<? super T> action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public T reduce(T identity, BinaryOperator<T> accumulator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<T> reduce(BinaryOperator<T> accumulator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <R> R collect(Supplier<R> supplier,
      BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <R, A> R collect(Collector<? super T, A, R> collector) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<T> min(Comparator<? super T> comparator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<T> max(Comparator<? super T> comparator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean anyMatch(Predicate<? super T> predicate) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean allMatch(Predicate<? super T> predicate) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean noneMatch(Predicate<? super T> predicate) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<T> findFirst() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<T> findAny() {
    throw new UnsupportedOperationException();
  }

}
