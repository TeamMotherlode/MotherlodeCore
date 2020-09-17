package motherlode.base.api;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.Motherlode;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RegisterItem {

    private String name = null;
    private Item item = null;
    private Registerable<? super Item> registerable = null;
    private AssetGenerator assetGenerator = null;
    private DataGenerator dataGenerator = null;
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

    public RegisterItem assets(AssetGenerator generator) {
        this.assetGenerator = generator;
        return this;
    }

    public RegisterItem data(DataGenerator generator) {
        this.dataGenerator = generator;
        return this;
    }

    public RegisterItem processor(Processor<? super Item> p) {
        this.processor = p;
        return this;
    }

    public Item register(String namespace) {
        Registerable<? super Item> registerable;

        if(this.registerable != null) registerable = this.registerable;
        else registerable = Registerable.item(this.item);

        Identifier id = Motherlode.id(namespace, this.name);

        registerable.register(id);

        if(this.processor != null) this.processor.accept(this.item);
        if(this.assetGenerator != null) MotherlodeAssets.addGenerator(id, this.assetGenerator);
        if(this.dataGenerator != null) MotherlodeData.addGenerator(id, this.dataGenerator);

        return this.item;
    }
}
