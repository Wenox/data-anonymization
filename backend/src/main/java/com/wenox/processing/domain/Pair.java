package com.wenox.processing.domain;

public class Pair<S, T> {
  private final S first;
  private final T second;

  private Pair(S first, T second) {
    this.first = first;
    this.second = second;
  }

  public static <S, T> Pair<S, T> of(S first, T second) {
    return new Pair<>(first, second);
  }

  public S getFirst() {
    return this.first;
  }

  public T getSecond() {
    return this.second;
  }

  public String toString() {
    return String.format("%s: %s", first, second);
  }
}
