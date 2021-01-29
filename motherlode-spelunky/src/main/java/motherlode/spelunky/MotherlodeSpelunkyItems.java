package motherlode.spelunky;

import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.CommonAssets;

public class MotherlodeSpelunkyItems {
    public static final Item SLIME_BUCKET = register("slime_bucket", new BucketItem(MotherlodeSpelunkyFluids.STILL_SLIME, new Item.Settings().group(ItemGroup.MISC)));

    private static <T extends Item> T register(String name, T item) {
        return Motherlode.register(
            Registerable.item(item),
            MotherlodeModule.id(name),
            item,
            CommonAssets.DEFAULT_ITEM_MODEL
        );
    }

    public static void init() {
    }
}
