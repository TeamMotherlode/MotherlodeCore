package motherlode.base.api;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RegisterBlock {

    private String name = null;
    private Block block = null;
    private Registerable<? super Block> registerable = null;
    private AssetGenerator assetGenerator = null;
    private DataGenerator dataGenerator = null;
    private Processor<? super Block> processor = null;

    public RegisterBlock() {
    }

    public RegisterBlock name(String name) {
        this.name = name;
        return this;
    }

    public RegisterBlock block(Block block) {
        this.block = block;
        return this;
    }

    public<T extends Block & Registerable<? super Block>> RegisterBlock registerableBlock(T block) {
        this.block = block;
        this.registerable = block;
        return this;
    }

    public RegisterBlock registerable(Registerable<? super Block> registerable) {
        this.registerable = registerable;
        return this;
    }

    public RegisterBlock assets(AssetGenerator generator) {
        this.assetGenerator = generator;
        return this;
    }

    public RegisterBlock data(DataGenerator generator) {
        this.dataGenerator = generator;
        return this;
    }

    public RegisterBlock processor(Processor<? super Block> p) {
        this.processor = p;
        return this;
    }

    public Block register(String namespace, Item.Settings itemSettings) {
        Registerable<? super Block> registerable;

        if(this.registerable != null) registerable = this.registerable;
        else registerable = Registerable.block(this.block, itemSettings);

        Identifier id = Motherlode.id(namespace, this.name);

        registerable.register(id);

        if(this.processor != null) this.processor.accept(this.block);
        if(this.assetGenerator != null) MotherlodeAssets.addGenerator(id, this.assetGenerator);
        if(this.dataGenerator != null) MotherlodeData.addGenerator(id, this.dataGenerator);

        return this.block;
    }
}
