package perf;


public interface BinaryOperator<T> {
  public T operate(T left, T right);
}