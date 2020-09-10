package com.github.payne.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class UniqueItemsContainer<T> {

    protected final Set<T> set = new HashSet<>();

    public UniqueItemsContainer(T... items) {
        set.addAll(Arrays.asList(items.clone()));
    }
}
