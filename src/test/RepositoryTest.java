import tmmc.json.JsonMod.Repository;
import arc.util.Log;

public class RepositoryTest {
    public static void main(String[] args) {
        Repository repository = new Repository("TheEE145", "TheMindustryModdingCore");

        Log.info(repository.asUrl());
        Log.info(repository.asJitpackUrl());
        Log.info(repository.name());
        Log.info(repository.user());
        Log.info(repository);

        //new Mods.LoadedMod(...).setRepo(repository.asUrl());
    }
}