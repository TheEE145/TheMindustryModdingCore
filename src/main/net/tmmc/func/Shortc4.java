package net.tmmc.func;

public interface Shortc4 {
    void get(short s1, short s2, short s3, short s4);

    default void getChar(char c1, char c2, char c3, char c4) {
        this.get((short) c1, (short) c2, (short) c3, (short) c4);
    }
}