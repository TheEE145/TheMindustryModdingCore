package tmmc.util;

import arc.func.Floatc;
import arc.func.Intc;
import arc.struct.*;

import tmmc.func.Bytec;
import tmmc.func.Longc;
import tmmc.func.Shortc;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ArrayUtils {
    @Contract(value = "null -> null; !null -> new", pure = true)
    public static<T> Iterator<T> iterator(T[] array) {
        if(array == null) {
            return null;
        }

        return new Iterator<>() {
            public int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < array.length;
            }

            @Override
            public T next() {
                return array[this.index++];
            }
        };
    }

    public static void forEach(@NotNull IntSeq seq, Intc intc) {
        int i = 0;
        while(i < seq.size) {
            intc.get(seq.get(i++));
        }
    }

    public static void forEach(@NotNull LongSeq seq, Longc longc) {
        int i = 0;
        while(i < seq.size) {
            longc.get(seq.get(i++));
        }
    }

    public static void forEach(@NotNull FloatSeq seq, Floatc floatc) {
        int i = 0;
        while(i < seq.size) {
            floatc.get(seq.get(i++));
        }
    }

    public static void forEach(@NotNull ShortSeq seq, Shortc shortc) {
        int i = 0;
        while(i < seq.size) {
            shortc.get(seq.get(i++));
        }
    }

    public static void forEach(@NotNull ByteSeq seq, Bytec bytec) {
        int i = 0;
        while(i < seq.size) {
            bytec.get(seq.get(i++));
        }
    }
}