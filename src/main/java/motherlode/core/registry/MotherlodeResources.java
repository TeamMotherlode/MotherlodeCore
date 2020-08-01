package motherlode.core.registry;

import com.google.common.collect.Lists;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.core.Motherlode;

import java.util.List;

/*
-----------------------------------------------------------------------------------------------------------------------------------
                  SIMPLIFY THE ASSET/DATA SYSTEM, ELIMINATING LONG LISTS OF ENTRIES LIKE defaultItemModelList
-----------------------------------------------------------------------------------------------------------------------------------
*/

//CURRENTLY UNUSED UNTIL EVERYONE IS HAPPY WITH THIS SIMPLER SYSTEM

public class MotherlodeResources {
    private static final List<Processor<ArtificeResourcePack.ClientResourcePackBuilder>> ASSETS = Lists.newArrayList();
    private static final List<Processor<ArtificeResourcePack.ServerResourcePackBuilder>> DATA = Lists.newArrayList();

    public static void addAsset(Processor<ArtificeResourcePack.ClientResourcePackBuilder> processor) {
        ASSETS.add(processor);
    }
    public static void addData(Processor<ArtificeResourcePack.ServerResourcePackBuilder> processor) {
        DATA.add(processor);
    }

    public static void initClient() {
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(Processor<ArtificeResourcePack.ClientResourcePackBuilder> p : ASSETS) {
                p.accept(pack);
            }
        });
    }
    public static void initServer() {
        Artifice.registerData(Motherlode.id("server_pack"), pack -> {
            for(Processor<ArtificeResourcePack.ServerResourcePackBuilder> p : DATA) {
                p.accept(pack);
            }
        });
    }
}
