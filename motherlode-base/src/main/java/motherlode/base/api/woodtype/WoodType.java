package motherlode.base.api.woodtype;

import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.MotherlodeVariantType;
import motherlode.base.api.Registerable;
import motherlode.base.block.DefaultSaplingBlock;
import motherlode.base.mixin.BlocksAccessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

/**
 * JavaDoc planned.
 */
public class WoodType extends MotherlodeVariantType<Block, WoodType> {
    private final PillarBlock log;
    private final PillarBlock strippedLog;
    private final Block wood;
    private final Block strippedWood;
    private final Block planks;
    private final LeavesBlock leaves;
    private final SaplingBlock sapling;

    private final Item.Settings itemSettings;

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator, Item.Settings itemSettings) {
        super(id);

        this.log = BlocksAccessor.callCreateLogBlock(logTopMapColor, logSideMapColor);
        this.strippedLog = BlocksAccessor.callCreateLogBlock(logTopMapColor, logTopMapColor);
        this.wood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.strippedWood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.planks = new Block(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.leaves = BlocksAccessor.callCreateLeavesBlock();
        this.sapling = new DefaultSaplingBlock(saplingGenerator.apply(this.log.getDefaultState(), this.leaves.getDefaultState()), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));

        this.itemSettings = itemSettings;
    }

    public PillarBlock getLog() {
        return this.log;
    }

    public PillarBlock getStrippedLog() {
        return this.strippedLog;
    }

    public Block getWood() {
        return this.wood;
    }

    public Block getStrippedWood() {
        return this.strippedWood;
    }

    public Block getPlanks() {
        return this.planks;
    }

    public LeavesBlock getLeaves() {
        return this.leaves;
    }

    public SaplingBlock getSapling() {
        return this.sapling;
    }

    @Override
    protected WoodType getThis() {
        return this;
    }

    @Override
    protected void registerBase(Identifier id) {
        Registerable.block(this.log, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        Registerable.block(this.strippedLog, this.itemSettings).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));
        Registerable.block(this.wood, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));
        Registerable.block(this.strippedWood, this.itemSettings).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));
        Registerable.block(this.planks, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));
        Registerable.block(this.leaves, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
        Registerable.block(this.sapling, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));

        StrippedBlockMap.INSTANCE.addStrippedBlock(this.log, this.strippedLog);
        StrippedBlockMap.INSTANCE.addStrippedBlock(this.wood, this.strippedWood);
    }

    @Override
    protected Block[] baseVariants() {
        return new Block[] { this.log, this.strippedLog, this.wood, this.strippedWood, this.planks, this.leaves, this.sapling };
    }

    @Override
    protected void registerOnClient(Identifier id) {
        BlockRenderLayerMap.INSTANCE.putBlock(this.leaves, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(this.sapling, RenderLayer.getCutout());
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        // Log
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_log"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_horizontal"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_horizontal"))
                .rotationX(90))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_log"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_top"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_log_horizontal"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column_horizontal"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_top"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log"));

        // Stripped log
        pack.addBlockState(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_horizontal"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_horizontal"))
                .rotationX(90))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_top"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log_horizontal"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column_horizontal"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_top"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));

        // Wood
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_wood"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_wood"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_wood")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_wood"))
                .rotationX(90))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_wood"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));

        // Stripped wood
        pack.addBlockState(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_wood"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_wood")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_wood"))
                .rotationX(90))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"), state -> state
            .parent(new Identifier("minecraft", "block/cube_column"))
            .texture("end", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));

        // Planks
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));

        // Leaves
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));

        // Sapling
        CommonAssets.DEFAULT_BLOCK_STATE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"), state -> state
            .parent(new Identifier("minecraft", "block/cross"))
            .texture("cross", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_sapling"))
        );

        CommonAssets.FLAT_BLOCK_ITEM_MODEL.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));

        pack.addLootTable(Motherlode.id(id.getNamespace(), "blocks/" + id.getPath() + "_leaves"), table -> table
            .type(new Identifier("minecraft", "block"))
            .pool(pool -> pool
                .rolls(1)
                .entry(entry -> entry
                    .type(new Identifier("minecraft", "alternatives"))
                    .child(child -> child
                        .type(new Identifier("minecraft", "item"))
                        .condition(new Identifier("minecraft", "alternative"), condition -> condition
                            .addArray("terms", terms -> terms
                                .addObject(term -> term
                                    .add("condition", "minecraft:match_tool")
                                    .addObject("predicate", predicate -> predicate
                                        .add("item", "minecraft:shears")
                                    )
                                )
                                .addObject(term -> term
                                    .add("condition", "minecraft:match_tool")
                                    .addObject("predicate", predicate -> predicate
                                        .addArray("enchantments", enchantments -> enchantments
                                            .addObject(enchantment -> enchantment
                                                .add("enchantment", "minecraft:silk_touch")
                                                .addObject("levels", levels -> levels
                                                    .add("min", 1)
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                        .name(Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"))
                    )
                    .child(child -> child
                        .type(new Identifier("minecraft", "item"))
                        .condition(new Identifier("minecraft", "survives_explosion"), condition -> {
                        })
                        .condition(new Identifier("minecraft", "table_bonus"), condition -> condition
                            .add("enchantment", "minecraft:fortune")
                            .addArray("chances", chances -> chances
                                .add(0.05)
                                .add(0.0625)
                                .add(0.083333336)
                                .add(0.1)
                            )
                        )
                        .name(Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"))
                    )
                )
            )
            .pool(pool -> pool
                .rolls(1)
                .entry(entry -> entry
                    .type(new Identifier("minecraft", "item"))
                    .condition(new Identifier("minecraft", "table_bonus"), condition -> condition
                        .add("enchantment", "minecraft:fortune")
                        .addArray("chances", chances -> chances
                            .add(0.2)
                            .add(0.022222223)
                            .add(0.025)
                            .add(0.033333335)
                            .add(0.1)
                        )
                    )
                    .function(new Identifier("minecraft", "set_count"), function -> function
                        .addObject("count", count -> count
                            .add("min", 1.0)
                            .add("max", 2.0)
                            .add("type", "minecraft:uniform")
                        )
                    )
                    .function(new Identifier("minecraft", "explosion_decay"), function -> {
                    })
                    .name(new Identifier("minecraft", "stick"))
                )
                .condition(new Identifier("minecraft", "inverted"), condition -> condition
                    .addObject("term", term -> term
                        .add("condition", "minecraft:alternative")
                        .addArray("terms", terms -> terms
                            .addObject(term2 -> term2
                                .add("condition", "minecraft:match_tool")
                                .addObject("predicate", predicate -> predicate
                                    .add("item", "minecraft:shears")
                                )
                            )
                            .addObject(term2 -> term2
                                .add("condition", "minecraft:match_tool")
                                .addObject("predicate", predicate -> predicate
                                    .addArray("enchantments", enchantments -> enchantments
                                        .addObject(enchantment -> enchantment
                                            .add("enchantment", "minecraft:silk_touch")
                                            .addObject("levels", levels -> levels
                                                .add("min", 1)
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );

        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));

        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "logs")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "leaves")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
    }
}
