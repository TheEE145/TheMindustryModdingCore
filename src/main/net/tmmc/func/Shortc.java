package net.tmmc.func;

public interface Shortc {
    void get(short number);

    default void getChar(char ch) {
        this.get((short) ch);
    }
}