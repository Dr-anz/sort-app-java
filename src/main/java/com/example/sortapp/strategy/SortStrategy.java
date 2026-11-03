package com.example.sortapp.strategy;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(T[] array, Comparator<? super T> comparator);
}
