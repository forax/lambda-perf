package perf;


public interface Mapper<T, R> {
  R map(T t);
}