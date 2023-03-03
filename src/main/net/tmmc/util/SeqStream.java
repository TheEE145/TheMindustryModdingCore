package net.tmmc.util;

import arc.func.Boolf;
import arc.func.Func;
import arc.struct.Seq;
import org.jetbrains.annotations.Contract;

public class SeqStream<T> {
    @Contract("null -> null; !null -> new")
    public static<T> SeqStream<T> of(Seq<T> vector) {
        if(vector == null) return null;
        SeqStream<T> stream = new SeqStream<>();
        stream.vector = vector.copy();
        return stream;
    }

    @Contract("null -> null; !null -> new")
    public static<T> SeqStream<T> of(T[] array) {
        if(array == null) return null;
        return of(Seq.with(array));
    }

    private Seq<T> vector;

    public SeqStream<T> reverse() {
        int length = this.vector.size;
        int len = (int) Math.floor(length/2f);

        for(int i = 0; i < len; i++) {
            T swap = this.vector.get(i);
            int end = length - i - 1;
            this.vector.set(i, this.vector.get(end));
            this.vector.set(end, swap);
        }

        return this.copy();
    }

    public<R> SeqStream<R> map(Func<T, R> mapper) {
        return SeqStream.of(this.vector.map(mapper));
    }

    public<R> SeqStream<R> flatMap(Func<T, Iterable<R>> mapper) {
        return SeqStream.of(this.vector.flatMap(mapper));
    }

    public SeqStream<T> filter(Boolf<T> predicate) {
        return SeqStream.of(this.vector.filter(predicate));
    }

    public SeqStream<T> copy() {
        return SeqStream.of(this.vector);
    }

    public Seq<T> get() {
        return this.vector;
    }

    @Override
    public String toString() {
        return "SeqStream{vector=" + vector + '}';
    }
}