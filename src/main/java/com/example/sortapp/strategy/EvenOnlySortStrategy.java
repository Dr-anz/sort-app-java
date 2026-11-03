package com.example.sortapp.strategy;

import java.util.*;
import java.util.function.ToIntFunction;

public class EvenOnlySortStrategy<T> implements SortStrategy<T> {
    private final ToIntFunction<T> extractor;

    public EvenOnlySortStrategy(ToIntFunction<T> extractor) {
        this.extractor = extractor;
    }

    @Override
    public void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0 || comparator == null) return;

        List<Integer> evenIndices = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            int val = extractor.applyAsInt(array[i]);
            if (val % 2 == 0) evenIndices.add(i);
        }

        T[] evens = (T[]) new Object[evenIndices.size()];
        for (int i = 0; i < evenIndices.size(); i++) {
            evens[i] = array[evenIndices.get(i)];
        }

        new BubbleSortStrategy<>().sort(evens, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            array[evenIndices.get(i)] = evens[i];
        }
    }
}
