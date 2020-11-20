package motherlode.base.api.woodtype;

import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.CommonAssets;
import motherlode.base.api.CommonData;
import motherlode.base.api.MotherlodeVariantType;
import motherlode.base.api.Registerable;
import motherlode.base.block.DefaultPressurePlateBlock;
import motherlode.base.block.DefaultSaplingBlock;
import motherlode.base.block.DefaultWoodenButtonBlock;
import motherlode.base.mixin.BlocksAccessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

/**
 * JavaDoc planned.
 */
public class WoodType extends MotherlodeVariantType<Block, WoodType> {
    private PillarBlock log;
    private PillarBlock strippedLog;
    private Block wood;
    private Block strippedWood;
    private Block planks;
    private WoodenButtonBlock button;
    private FenceBlock fence;
    private FenceGateBlock fenceGate;
    private PressurePlateBlock pressurePlate;
    private LeavesBlock leaves;
    private SaplingBlock sapling;
    private FlowerPotBlock pottedSapling;

    private final MapColor logTopMapColor;
    private final MapColor logSideMapColor;
    private final BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator;

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator) {
        super(id);

        this.logTopMapColor = logTopMapColor;
        this.logSideMapColor = logSideMapColor;
        this.saplingGenerator = saplingGenerator;
    }

    @Override
    protected WoodType getThis() {
        return this;
    }

    private final static Item.Settings BUILDING_BLOCKS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);
    private final static Item.Settings REDSTONE = new Item.Settings().group(ItemGroup.REDSTONE);
    private final static Item.Settings DECORATIONS = new Item.Settings().group(ItemGroup.DECORATIONS);

    @Override
    protected void registerBase(Identifier id) {
        this.log = BlocksAccessor.callCreateLogBlock(this.logTopMapColor, this.logSideMapColor);
        this.strippedLog = BlocksAccessor.callCreateLogBlock(this.logTopMapColor, this.logTopMapColor);
        this.wood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.strippedWood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.planks = new Block(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.button = new DefaultWoodenButtonBlock(FabricBlockSettings.of(Material.SUPPORTED).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        this.fence = new FenceBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.fenceGate = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.pressurePlate = new DefaultPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        this.leaves = BlocksAccessor.callCreateLeavesBlock();
        this.sapling = new DefaultSaplingBlock(saplingGenerator.apply(this.log.getDefaultState(), this.leaves.getDefaultState()), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
        this.pottedSapling = new FlowerPotBlock(this.sapling, FabricBlockSettings.of(Material.SUPPORTED).breakInstantly().nonOpaque());

        Registerable.block(this.log, BUILDING_BLOCKS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        Registerable.block(this.strippedLog, BUILDING_BLOCKS).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));
        Registerable.block(this.wood, BUILDING_BLOCKS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));
        Registerable.block(this.strippedWood, BUILDING_BLOCKS).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));
        Registerable.block(this.planks, BUILDING_BLOCKS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));
        Registerable.block(this.button, REDSTONE).register(Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        Registerable.block(this.fence, DECORATIONS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        Registerable.block(this.fenceGate, REDSTONE).register(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));
        Registerable.block(this.pressurePlate, REDSTONE).register(Motherlode.id(id.getNamespace(), id.getPath() + "_pressure_plate"));
        Registerable.block(this.leaves, DECORATIONS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
        Registerable.block(this.sapling, DECORATIONS).register(Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));
        Registerable.block(this.pottedSapling).register(Motherlode.id(id.getNamespace(), "potted_" + id.getPath() + "_sapling"));

        StrippedBlockMap.INSTANCE.addStrippedBlock(this.log, this.strippedLog);
        StrippedBlockMap.INSTANCE.addStrippedBlock(this.wood, this.strippedWood);
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

    public WoodenButtonBlock getButton() {
        return this.button;
    }

    public FenceBlock getFence() {
        return this.fence;
    }

    public FenceGateBlock getFenceGate() {
        return this.fenceGate;
    }

    public PressurePlateBlock getPressurePlate() {
        return this.pressurePlate;
    }

    public LeavesBlock getLeaves() {
        return this.leaves;
    }

    public SaplingBlock getSapling() {
        return this.sapling;
    }

    public FlowerPotBlock getPottedSapling() {
        return this.pottedSapling;
    }

    @Override
    protected Block[] baseVariants() {
        return new Block[] { this.log, this.strippedLog, this.wood, this.strippedWood, this.planks, this.button, this.fence, this.fenceGate, this.pressurePlate, this.leaves, this.sapling, this.pottedSapling };
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

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_top"),
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log"));

        CommonAssets.HORIZONTAL_CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log_top"),
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log_horizontal"));

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

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_top"),
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));

        CommonAssets.HORIZONTAL_CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log_top"),
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log_horizontal"));

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

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log"),
            Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));

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

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log"),
            Motherlode.id(id.getNamespace(), "block/stripped_" + id.getPath() + "_log")
        ).accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));

        // Planks
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));

        // Button
        WoodTypeAssets.BUTTON.accept(pack, id);

        // Fence
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_fence"), state -> state
            .multipartCase(multipartCase -> multipartCase
                .apply(variant -> variant
                    .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_post"))
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("north", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_side"))
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("east", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_side"))
                    .rotationY(90)
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("south", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_side"))
                    .rotationY(180)
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("west", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_side"))
                    .rotationY(270)
                    .uvlock(true)
                )
            )
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_post"), model -> model
            .parent(new Identifier("minecraft", "block/fence_post"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_side"), model -> model
            .parent(new Identifier("minecraft", "block/fence_side"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_inventory"), model -> model
            .parent(new Identifier("minecraft", "block/fence_inventory"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        CommonAssets.BLOCK_ITEM_FUNCTION.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_fence"))
            .accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_inventory"));

        // Fence gate
        WoodTypeAssets.FENCE_GATE.accept(pack, id);

        // Pressure plate
        WoodTypeAssets.PRESSURE_PLATE.accept(pack, id);

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
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_pressure_plate"));

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

        CommonData.BLOCK_TAG.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_logs")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_logs")).accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_logs")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_logs")).accept(pack, Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));
        CommonData.BLOCK_TAG_INCLUDE.apply(new Identifier("minecraft", "logs_that_burn")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_logs"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "planks")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_planks"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_buttons")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_fences")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "fence_gates")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "pressure_plates")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_pressure_plate"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "leaves")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "saplings")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));
    }
}
