package perf;


import java.util.Collection;

public interface Stream<T> {
  Stream<T> filter(Predicate<? super T> predicate);

  <R> Stream<R> map(Mapper<? super T, ? extends R> mapper);

  void forEach(Block<? super T> block);

  <A extends Collection<? super T>> A into(A target);

  T reduce(T base, BinaryOperator<T> op);

  int reduce(int base, IntBinaryOperator op);
  
  T findFirst(T defaultValue);
}
