import tmmc.json.BundleCreation;
import tmmc.json.ModJsonData;

public class Tests {
    public static void print(Object obj) {
        System.out.println(obj);
    }

    public static void main(String[] args) {
        var data = ModJsonData.from(OctoChainsaw.modJson);
        BundleCreation creation = data.getBundleCreation();

        if(creation != null) {
            print(creation.getRoot().equals(data));
        } else {
            print("false, it`s null");
        }

        print(data.toJson());
    }
}