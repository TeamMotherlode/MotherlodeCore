package motherlode.core.registry;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import static com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;

import motherlode.core.Motherlode;
import motherlode.core.api.BlockProperties;
import motherlode.core.assets.BlockTint;
import motherlode.core.assets.BlockModel;
import motherlode.core.assets.ItemModel;
import motherlode.core.block.PotBlock;
import motherlode.core.registry.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets {

	public static void init() {}

    public static List<String> defaultStates = new ArrayList<>();
    public static List<String> cutout = new ArrayList<>();
    public static Map<String, Map.Entry<BlockModel, Boolean>> blockModels = new HashMap<>();
    public static Map<String, ItemModel> itemModels = new HashMap<>();
    public static Map<Block, BlockTint> blockTints = new HashMap<>();

    public static void register(BlockProperties prop, String id, Block block) {
        if (prop.defaultState) defaultStates.add(id);
        if (prop.cutout) cutout.add(id);
        if (prop.blockTint != null) blockTints.put(block, prop.blockTint);

        blockModels.put(id, new AbstractMap.SimpleEntry<>(prop.blockModel, prop.blockModelBoolean));
        itemModels.putIfAbsent(id, prop.itemModel);
    }

    public static void clear() {
        defaultStates = null;
        blockModels = null;
        itemModels = null;
        cutout = null;
        blockTints = null;
    }

    public static void register(){
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(String blockId : defaultStates) {
                pack.addBlockState(Motherlode.id(blockId), state -> state
                        .variant("", settings -> settings.model(Motherlode.id("block/" + blockId)))
                );
            }

            for (Map.Entry<String, Map.Entry<BlockModel, Boolean>> entry : blockModels.entrySet())
                entry.getValue().getKey().register(pack, entry.getKey(), entry.getValue().getValue());

            for (Map.Entry<String, ItemModel> entry : itemModels.entrySet())
                entry.getValue().register(pack,entry.getKey());

            registerPotions(pack);
            registerPots(pack);
            registerRope(pack);

        });

        registerModelPredicateProviders();

        for (String id : cutout)
            BlockRenderLayerMap.INSTANCE.putBlock(Registry.BLOCK.get(Motherlode.id(id)), RenderLayer.getCutout());

        for (Map.Entry<Block, BlockTint> entry : blockTints.entrySet())
            entry.getValue().register(entry.getKey());

        ColorProviderRegistry.BLOCK.register((state, _world, _pos, _tintIndex) -> state.get(PotBlock.COLOR).getColor(), MotherlodeBlocks.POT);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                BiomeColors.getGrassColor(world, pos), MotherlodeBlocks.WATERPLANT);

        clear();
    }

    private static void registerPotions(ClientResourcePackBuilder pack) {
        for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
            if (!info.useDefaultModel)
                pack.addItemModel(Motherlode.id("potions/" + info.model), (model) -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id("item/potions/" + info.model)));
        }

        pack.addItemModel(Motherlode.id("potions/default"), (model) -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("item/potion_overlay"))
                .texture("layer1", new Identifier("item/potion")));

        pack.addItemModel(new Identifier("potion"), (model) -> {
            model.parent(new Identifier("item/generated"));
            model.texture("layer0", new Identifier("item/potion"));
            model.texture("layer1", new Identifier("item/potion_overlay"));

            for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                if (info.model == null || info.useDefaultModel)
                    model.override( override -> floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id("item/potions/default")) );
                else
                    model.override( override -> floatPredicate(override, "potion_type", info.predicateValue).model(Motherlode.id("item/potions/" + info.model)) );

            }
        });
    }

    private static void registerPots(ClientResourcePackBuilder pack) {
        pack.addBlockState(Motherlode.id("pot"), state -> {
            for (int i = 0; i <= PotBlock.maxPattern; i++) {
                int ii = i;
                pack.addBlockModel(Motherlode.id("pot_with_overlay_" + i), model -> model
                        .parent(Motherlode.id("block/pot"))
                        .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
                );
                state.variant("pattern="+i, settings -> settings.model(Motherlode.id("block/pot_with_overlay_" + ii)));
            }
        });

        pack.addItemModel(Motherlode.id("pot_template"), model2 -> model2
                .parent(Motherlode.id("block/pot"))
                .texture("overlay", Motherlode.id("block/pots/pot_overlay_1"))

                .display("thirdperson_righthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(66F, 135F, 0F).translation(0,4,4))
                .display("thirdperson_lefthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(66F, 135F, 0F).translation(0,4,4))
                .display("firstperson_righthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(0F, 135F, 0F).translation(2,2,-2))
                .display("firstperson_lefthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(0F, 135F, 0F).translation(0,5,10))
        );

        pack.addItemModel(Motherlode.id("pot"), model -> {
            for (int i = 0; i <= PotBlock.maxPattern; i++) {
                float pattern = i / 100F;
                int ii = i;
                model.override(override -> floatPredicate(override, "pot_pattern", pattern).model(Motherlode.id("item/pot_" + ii)));

                pack.addItemModel(Motherlode.id("pot_" + ii), model2 -> model2
                        .parent(Motherlode.id("item/pot_template"))
                        .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
                );

            }
        });
    }

    private static void registerRope(ClientResourcePackBuilder pack) {
        int[] stackCounts = new int[]{0,8,16,24,32,40,48,56,64};

        for (int stackCount : stackCounts) {
            pack.addItemModel(Motherlode.id("rope" + stackCount), builder -> builder
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("item/rope/rope" + stackCount))
            );
        }

        pack.addItemModel(Motherlode.id("rope"), builder -> {
            for (int stackCount : stackCounts)
                builder.override(override -> floatPredicate(override, "stack_count", stackCount / 100F).model(Motherlode.id("item/rope" + stackCount)));
        });

        pack.addBlockState(Motherlode.id("rope"), builder -> {
            String[] directions = new String[]{"south", "west", "north", "east"};
            for (int i = 0; i < directions.length; i++) {
                int ii = i;
                builder.variant("bottom=false,connected=up,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_top")).rotationY(ii *90));
                builder.variant("bottom=true,connected=up,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_top_bottom")).rotationY(ii *90));
                builder.variant("bottom=false,connected=side,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_side")).rotationY(ii *90));
                builder.variant("bottom=true,connected=side,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_side_bottom")).rotationY(ii *90));
            }
            builder.variant("bottom=false,connected=none", settings -> settings.model(Motherlode.id("block/rope")));
            builder.variant("bottom=true,connected=none", settings -> settings.model(Motherlode.id("block/rope_bottom")));
        });
    }


    private static ModelBuilder.Override floatPredicate(ModelBuilder.Override override, String name, Number value) {
	    override.with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
	    return override;
    }

    private static void registerModelPredicateProviders() {
        FabricModelPredicateProviderRegistry.register(Items.POTION, new Identifier("potion_type"), (itemStack, _world, _entity) -> {
            MotherlodePotions.PotionModelInfo potion = MotherlodePotions.potionModelInfos.get( PotionUtil.getPotion(itemStack) );
            return potion == null ? 1 : potion.predicateValue;
        });

        FabricModelPredicateProviderRegistry.register(new Identifier("stack_count"), ( itemStack,  _world,  _entity) -> itemStack.getCount() / 100F);

        FabricModelPredicateProviderRegistry.register(MotherlodeBlocks.POT.asItem(), new Identifier("pot_pattern"), (itemStack, _world, _entity) -> {
            CompoundTag tag = itemStack.getTag();
            if (tag == null || !tag.contains("BlockStateTag"))
                return 0;
            tag = tag.getCompound("BlockStateTag");
            if (tag == null || !tag.contains("pattern"))
                return 0;

            return Integer.parseInt(tag.getString("pattern")) / 100F;
        });
    }



}