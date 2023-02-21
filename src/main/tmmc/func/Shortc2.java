package tmmc.func;

public interface Shortc2 {
    void get(short s1, short s2);

    default void getChar(char ch1, char ch2) {
        this.get((short) ch1, (short) ch2);
    }
}