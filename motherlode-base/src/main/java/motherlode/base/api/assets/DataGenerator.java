package motherlode.base.api.assets;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

@FunctionalInterface
public interface DataGenerator {
    /**
     * This is called to register data using Artifice.
     *
     * @param pack Data pack builder to register assets to.
     */
    void accept(ArtificeResourcePack.ServerResourcePackBuilder pack);
}
