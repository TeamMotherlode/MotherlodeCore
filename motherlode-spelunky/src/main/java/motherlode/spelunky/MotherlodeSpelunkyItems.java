package motherlode.spelunky;

import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.spelunky.fluid.SlimeFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class MotherlodeSpelunkyItems {

    public static final Item SLIME_BUCKET = Registry.register(Registry.ITEM, MotherlodeModule.id("slime_bucket"), new BucketItem(MotherlodeSpelunkyFluids.STILL_SLIME, new Item.Settings().group(ItemGroup.MISC)));
    private static <T extends Item> T register(String name, T item) {
        //return Motherlode.register(Registerable.item(item), MotherlodeModule.id(name));
        return null;
    }

    public static void init(){

    }


}
