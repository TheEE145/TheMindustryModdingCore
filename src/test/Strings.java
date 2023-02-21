import arc.util.Log;
import net.tmmc.util.StringUtils;

public class Strings {
    public static void main(String[] args) {
       // for(short i = 0; i < Short.MAX_VALUE; i++) {
       //     char ch = (char) i;
       //     Log.info(i + " = " + ch);
       //     Log.info(StringUtils.toLowerCase(ch));
       //     Log.info(StringUtils.toUpperCase(ch));
       // }

        String kebab = "example-block-name";
        String camel = "ExampleBlockName";
        String space;

        Log.info(StringUtils.toSpace(kebab));
        Log.info(space = StringUtils.toSpace(camel));
        Log.info(StringUtils.toSpace(space));
    }
}