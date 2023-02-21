import tmmc.util.SeqStream;
import arc.struct.Seq;
import arc.util.Log;

public class StreamTest {
    public static void main(String[] args) {
        Log.info(SeqStream.of(Seq.with("a", "b", "c")).reverse());
        Log.info(SeqStream.of(Seq.with("a", "b", "c", "d")).reverse());
    }
}