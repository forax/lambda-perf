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
    ADHOC {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithAdhocImpl.asStream(iterable);
      }
    },
    MH {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithMH.asStream(iterable);
      }
    },
    MH2 {
      @Override
      public <T> Stream<T> asStream(Iterable<? extends T> iterable) {
        return StreamWithMH2.asStream(iterable);
      }
    }
  }
}