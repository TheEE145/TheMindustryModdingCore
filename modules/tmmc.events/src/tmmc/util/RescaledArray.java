package tmmc.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class RescaledArray<T> implements Iterable<T>
{
    private Object[] elements;

    @Contract(pure = true)
    public RescaledArray(int scale) {
        this.elements = new Object[scale];
    }

    @Contract(pure = true)
    public RescaledArray() {
        this(0);
    }

    @Contract(pure = true)
    public RescaledArray(T[] elements) {
        this.elements = elements;
    }

    public void expand(int length) {
        this.rescale(this.length() + length);
    }

    public void rescale(int length) {
        if(length < 0) {
            throw new RuntimeException("value must be 0 or bigger");
        }

        if(length == 0) {
            this.elements = new Object[0];
        }

        if(length == this.length()) {
            return;
        }

        Object[] new1 = new Object[length];
        for(int i = 0; i < this.elements.length; i++) {
            try {
                new1[i] = this.elements[i];
            } catch(Throwable ignored) {
                //if new array length < elements length
                break;
            }
        }

        this.elements = new1;
    }

    public int length() {
        return this.elements.length;
    }

    public T get(int index) {
        try {
            return (T) this.elements[index];
        } catch(ClassCastException exception) {
            throw new RuntimeException("array is broken", exception);
        }
    }

    public boolean editFirst(T value) {
        return this.edit(0, value);
    }

    public boolean editLast(T value) {
        return this.edit(this.length() - 1, value);
    }

    public boolean edit(int index, T value) {
        try {
            this.elements[index] = value;
            return true;
        } catch(Throwable ignored) {
            return false;
        }
    }

    public void apply(T[] array) {
        this.elements = array;
    }

    public T[] get() {
        return (T[]) this.elements;
    }

    public Object[] getChecked() {
        return this.elements;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.elements);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            public int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < length() - 1;
            }

            @Override
            public T next() {
                return get(this.index++);
            }
        };
    }
}