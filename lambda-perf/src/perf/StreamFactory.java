package perf;


interface StreamFactory {
  public <T> Stream<T> asStream(Iterable<? extends T> iterable);
  
  enum Impl implements StreamFactory {
    ITERABLE {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithIterable.asStream(iterable);
      }
    },
    SINK {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithSink.asStream(iterable);
      }
    },
    REDUCER {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithReducer.asStream(iterable);
      }
    },
    METHODHANDLE {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithMethodHandle.asStream(iterable);
      }
    }
  }
}