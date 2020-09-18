package motherlode.base.api;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegisterBlock {

    private String name = null;
    private Block block = null;
    private Registerable<? super Block> registerable = null;
    private AssetProcessor assetProcessor = null;
    private DataProcessor dataProcessor = null;
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

    public RegisterBlock assets(AssetProcessor generator) {
        this.assetProcessor = generator;
        return this;
    }

    public RegisterBlock data(DataProcessor generator) {
        this.dataProcessor = generator;
        return this;
    }

    public RegisterBlock processor(Processor<? super Block> p) {
        this.processor = p;
        return this;
    }

    public Block register(String namespace, Item.Settings itemSettings) {
        return Motherlode.register(
                this.registerable,
                Motherlode.id(namespace, this.name),
                this.block,
                this.processor,
                this.assetProcessor,
                this.dataProcessor);
    }
}
