package motherlode.base.api;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.Motherlode;
import net.minecraft.item.Item;

public class RegisterItem {

    private String name = null;
    private Item item = null;
    private Registerable<? super Item> registerable = null;
    private AssetProcessor assetProcessor = null;
    private DataProcessor dataProcessor = null;
    private Processor<? super Item> processor = null;

    public RegisterItem() {
    }

    public RegisterItem name(String name) {
        this.name = name;
        return this;
    }

    public RegisterItem item(Item item) {
        this.item = item;
        return this;
    }

    public<T extends Item & Registerable<? super Item>> RegisterItem registerableItem(T item) {
        this.item = item;
        this.registerable = item;
        return this;
    }

    public RegisterItem registerable(Registerable<? super Item> registerable) {
        this.registerable = registerable;
        return this;
    }

    public RegisterItem assets(AssetProcessor generator) {
        this.assetProcessor = generator;
        return this;
    }

    public RegisterItem data(DataProcessor generator) {
        this.dataProcessor = generator;
        return this;
    }

    public RegisterItem processor(Processor<? super Item> p) {
        this.processor = p;
        return this;
    }

    public Item register(String namespace) {
        return Motherlode.register(
                this.registerable,
                Motherlode.id(namespace, this.name),
                this.item,
                this.processor,
                this.assetProcessor,
                this.dataProcessor);
    }
}
