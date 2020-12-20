package motherlode.base.api.assets;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;

@FunctionalInterface
public interface AssetsGenerator {
    /**
     * This is called to register assets using Artifice.
     *
     * @param pack Resource pack builder to register assets to.
     */
    void accept(ArtificeResourcePack.ClientResourcePackBuilder pack);
}
