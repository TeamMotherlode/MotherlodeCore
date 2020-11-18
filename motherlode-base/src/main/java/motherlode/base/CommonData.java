package motherlode.base;

import java.util.function.Function;
import net.minecraft.util.Identifier;
import motherlode.base.api.DataProcessor;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class CommonData {
    public static final DataProcessor DEFAULT_BLOCK_LOOT_TABLE = (pack, id) ->
        pack.addLootTable(Motherlode.id(id.getNamespace(), "blocks/" + id.getPath()), table -> table
            .type(new Identifier("minecraft", "block"))
            .pool(pool -> pool
                .rolls(1)
                .entry(entry -> entry
                    .type(new Identifier("minecraft", "item"))
                    .name(id)
                )
                .condition(new Identifier("minecraft", "survives_explosion"), TypedJsonBuilder::build
                )
            )
        );

    public static final String COMMON_NAMESPACE = "c";

    public static final Function<Identifier, DataProcessor> ITEM_TAG = tagId -> (pack, id) ->
        pack.addItemTag(tagId, tag -> tag
            .replace(false)
            .value(id));

    public static final Function<Identifier, DataProcessor> BLOCK_TAG = tagId -> ITEM_TAG.apply(tagId).after((pack, id) ->
        pack.addBlockTag(tagId, tag -> tag
            .replace(false)
            .value(id)));
}
