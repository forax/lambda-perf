package perf;


public interface Block<T> {
  void accept(T t);
}