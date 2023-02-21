import arc.util.Log;
import tmmc.util.StringUtils;

public class Strings {
    public static void main(String[] args) {
        for(short i = 'A'; i <= 'Y'+1; i++) {
            Log.info(StringUtils.toLowerCase((char) i));
        }
    }
}