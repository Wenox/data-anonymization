package com.wenox.processing.service.operations;

public interface FacadeFactory<K, V> {

  V getFacade(K key);
}
