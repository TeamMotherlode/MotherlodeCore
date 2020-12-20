package motherlode.base.api.woodtype;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.item.Item;

@FunctionalInterface
public interface WoodTypeItemSettingsFunction extends BiFunction<WoodType.Variant, Function<WoodType.Variant, Optional<Item.Settings>>, Optional<Item.Settings>> {
    @Override
    Optional<Item.Settings> apply(WoodType.Variant variant, Function<WoodType.Variant, Optional<Item.Settings>> vanilla);
}
