package com.example.sortapp.strategy;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {
    @Override
    public void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length <= 1 || comparator == null) return;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    T tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
