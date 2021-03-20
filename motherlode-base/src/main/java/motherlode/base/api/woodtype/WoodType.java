package motherlode.base.api.woodtype;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
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
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.base.block.DefaultPressurePlateBlock;
import motherlode.base.block.DefaultSaplingBlock;
import motherlode.base.block.DefaultWoodenButtonBlock;
import motherlode.base.mixin.BlocksAccessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

/**
 * JavaDoc planned.
 */
public class WoodType extends MotherlodeVariantType<Block, WoodType> {
    private static final Item.Settings BUILDING_BLOCKS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);
    private static final Item.Settings REDSTONE = new Item.Settings().group(ItemGroup.REDSTONE);
    private static final Item.Settings DECORATIONS = new Item.Settings().group(ItemGroup.DECORATIONS);

    private static final Function<Variant, Optional<Item.Settings>> VANILLA_ITEM_SETTINGS_FUNCTION = variant -> {
        switch (variant) {
            case LOG:
            case STRIPPED_LOG:
            case WOOD:
            case STRIPPED_WOOD:
            case PLANKS:
                return Optional.of(BUILDING_BLOCKS);
            case BUTTON:
            case FENCE_GATE:
            case PRESSURE_PLATE:
                return Optional.of(REDSTONE);
            case FENCE:
            case LEAVES:
            case SAPLING:
                return Optional.of(DECORATIONS);
            case POTTED_SAPLING:
            default:
                return Optional.empty();
        }
    };

    public static final WoodType OAK = new WoodType(new Identifier("minecraft", "oak"), null, null, null, null, null).withoutBase().register();
    public static final WoodType DARK_OAK = new WoodType(new Identifier("minecraft", "dark_oak"), null, null, null, null, null).withoutBase().register();
    public static final WoodType BIRCH = new WoodType(new Identifier("minecraft", "birch"), null, null, null, null, null).withoutBase().register();
    public static final WoodType SPRUCE = new WoodType(new Identifier("minecraft", "spruce"), null, null, null, null, null).withoutBase().register();
    public static final WoodType JUNGLE = new WoodType(new Identifier("minecraft", "jungle"), null, null, null, null, null).withoutBase().register();
    public static final WoodType ACACIA = new WoodType(new Identifier("minecraft", "acacia"), null, null, null, null, null).withoutBase().register();
    public static final WoodType WARPED = new WoodType(new Identifier("minecraft", "warped"), null, null, null, null, null).withoutBase().register();
    public static final WoodType CRIMSON = new WoodType(new Identifier("minecraft", "crimson"), null, null, null, null, null).withoutBase().register();

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
    private final MapColor woodMapColor;
    private final BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator;
    private final WoodTypeItemSettingsFunction itemSettingsFunction;

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator) {
        this(id, logTopMapColor, logSideMapColor, logTopMapColor, saplingGenerator, (variant, vanilla) -> vanilla.apply(variant));
    }

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, MapColor woodMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator) {
        this(id, logTopMapColor, logSideMapColor, woodMapColor, saplingGenerator, (variant, vanilla) -> vanilla.apply(variant));
    }

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, MapColor woodMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator, WoodTypeItemSettingsFunction itemSettingsFunction) {
        super(id);

        this.logTopMapColor = logTopMapColor;
        this.logSideMapColor = logSideMapColor;
        this.woodMapColor = woodMapColor;
        this.saplingGenerator = saplingGenerator;
        this.itemSettingsFunction = itemSettingsFunction;
    }

    @Override
    protected WoodType getThis() {
        return this;
    }

    @Override
    protected void registerBase(Identifier id) {
        this.log = BlocksAccessor.callCreateLogBlock(this.logTopMapColor, this.logSideMapColor);
        this.strippedLog = BlocksAccessor.callCreateLogBlock(this.logTopMapColor, this.logTopMapColor);
        this.wood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.strippedWood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, this.logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.planks = new Block(FabricBlockSettings.of(Material.WOOD, this.woodMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.button = new DefaultWoodenButtonBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        this.fence = new FenceBlock(FabricBlockSettings.of(Material.WOOD, this.woodMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.fenceGate = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, this.woodMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.pressurePlate = new DefaultPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, this.woodMapColor).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        this.leaves = BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS);
        this.sapling = new DefaultSaplingBlock(saplingGenerator.apply(this.log.getDefaultState(), this.leaves.getDefaultState()), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
        this.pottedSapling = new FlowerPotBlock(this.sapling, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());

        registerBlock(id, this.itemSettingsFunction, Variant.LOG, this.log, name -> name + "_log");
        registerBlock(id, this.itemSettingsFunction, Variant.STRIPPED_LOG, this.strippedLog, name -> "stripped_" + name + "_log");
        registerBlock(id, this.itemSettingsFunction, Variant.WOOD, this.wood, name -> name + "_wood");
        registerBlock(id, this.itemSettingsFunction, Variant.STRIPPED_WOOD, this.strippedWood, name -> "stripped_" + name + "_wood");
        registerBlock(id, this.itemSettingsFunction, Variant.PLANKS, this.planks, name -> name + "_planks");
        registerBlock(id, this.itemSettingsFunction, Variant.BUTTON, this.button, name -> name + "_button");
        registerBlock(id, this.itemSettingsFunction, Variant.FENCE, this.fence, name -> name + "_fence");
        registerBlock(id, this.itemSettingsFunction, Variant.FENCE_GATE, this.fenceGate, name -> name + "_fence_gate");
        registerBlock(id, this.itemSettingsFunction, Variant.PRESSURE_PLATE, this.pressurePlate, name -> name + "_pressure_plate");
        registerBlock(id, this.itemSettingsFunction, Variant.LEAVES, this.leaves, name -> name + "_leaves");
        registerBlock(id, this.itemSettingsFunction, Variant.SAPLING, this.sapling, name -> name + "_sapling");
        registerBlock(id, this.itemSettingsFunction, Variant.POTTED_SAPLING, this.pottedSapling, name -> "potted_" + name + "_sapling");

        StrippedBlockMap.INSTANCE.addStrippedBlock(this.log, this.strippedLog);
        StrippedBlockMap.INSTANCE.addStrippedBlock(this.wood, this.strippedWood);
    }

    private static void registerBlock(Identifier id, WoodTypeItemSettingsFunction itemSettingsFunction, Variant variant, Block block, UnaryOperator<String> name) {
        itemSettingsFunction.apply(variant, VANILLA_ITEM_SETTINGS_FUNCTION).map(settings -> Registerable.block(block, settings))
            .orElseGet(() -> Registerable.block(block)).register(Motherlode.id(id, name));
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
        pack.addBlockState(Motherlode.id(id, name -> name + "_log"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_log_horizontal"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_log")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_log_horizontal"))
                .rotationX(90))
        );

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/" + name + "_log_top"),
            Motherlode.id(id, name -> "block/" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> name + "_log"));

        CommonAssets.HORIZONTAL_CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/" + name + "_log_top"),
            Motherlode.id(id, name -> "block/" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> name + "_log_horizontal"));

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id, name -> name + "_log"));

        // Stripped log
        pack.addBlockState(Motherlode.id(id, name -> "stripped_" + name + "_log"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_log_horizontal"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_log")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_log_horizontal"))
                .rotationX(90))
        );

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/stripped_" + name + "_log_top"),
            Motherlode.id(id, name -> "block/stripped_" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_log"));

        CommonAssets.HORIZONTAL_CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/stripped_" + name + "_log_top"),
            Motherlode.id(id, name -> "block/stripped_" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_log_horizontal"));

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_log"));

        // Wood
        pack.addBlockState(Motherlode.id(id, name -> name + "_wood"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_wood"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_wood")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id, name -> "block/" + name + "_wood"))
                .rotationX(90))
        );

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/" + name + "_log"),
            Motherlode.id(id, name -> "block/" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> name + "_wood"));

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id, name -> name + "_wood"));

        // Stripped wood
        pack.addBlockState(Motherlode.id(id, name -> "stripped_" + name + "_wood"), state -> state
            .variant("axis=x", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_wood"))
                .rotationX(90)
                .rotationY(90))
            .variant("axis=y", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_wood")))
            .variant("axis=z", variant -> variant
                .model(Motherlode.id(id, name -> "block/stripped_" + name + "_wood"))
                .rotationX(90))
        );

        CommonAssets.CUBE_COLUMN.apply(
            Motherlode.id(id, name -> "block/stripped_" + name + "_log"),
            Motherlode.id(id, name -> "block/stripped_" + name + "_log")
        ).accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_wood"));

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_wood"));

        // Planks
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id, name -> name + "_planks"));

        // Button
        WoodTypeAssets.BUTTON.accept(pack, id);

        // Fence
        pack.addBlockState(Motherlode.id(id, name -> name + "_fence"), state -> state
            .multipartCase(multipartCase -> multipartCase
                .apply(variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_fence_post"))
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("north", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_fence_side"))
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("east", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_fence_side"))
                    .rotationY(90)
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("south", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_fence_side"))
                    .rotationY(180)
                    .uvlock(true)
                )
            )
            .multipartCase(multipartCase -> multipartCase
                .when("west", "true")
                .apply(variant -> variant
                    .model(Motherlode.id(id, name -> "block/" + name + "_fence_side"))
                    .rotationY(270)
                    .uvlock(true)
                )
            )
        );

        pack.addBlockModel(Motherlode.id(id, name -> name + "_fence_post"), model -> model
            .parent(new Identifier("minecraft", "block/fence_post"))
            .texture("texture", Motherlode.id(id, name -> "block/" + name + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id, name -> name + "_fence_side"), model -> model
            .parent(new Identifier("minecraft", "block/fence_side"))
            .texture("texture", Motherlode.id(id, name -> "block/" + name + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id, name -> name + "_fence_inventory"), model -> model
            .parent(new Identifier("minecraft", "block/fence_inventory"))
            .texture("texture", Motherlode.id(id, name -> "block/" + name + "_planks"))
        );

        CommonAssets.BLOCK_ITEM_FUNCTION.apply(Motherlode.id(id, name -> name + "_fence"))
            .accept(pack, Motherlode.id(id, name -> name + "_fence_inventory"));

        // Fence gate
        WoodTypeAssets.FENCE_GATE.accept(pack, id);

        // Pressure plate
        WoodTypeAssets.PRESSURE_PLATE.accept(pack, id);

        // Leaves
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id, name -> name + "_leaves"));

        // Sapling
        CommonAssets.DEFAULT_BLOCK_STATE.accept(pack, Motherlode.id(id, name -> name + "_sapling"));

        pack.addBlockModel(Motherlode.id(id, name -> name + "_sapling"), state -> state
            .parent(new Identifier("minecraft", "block/cross"))
            .texture("cross", Motherlode.id(id, name -> "block/" + name + "_sapling"))
        );

        CommonAssets.FLAT_BLOCK_ITEM_MODEL.accept(pack, Motherlode.id(id, name -> name + "_sapling"));
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_log"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_log"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_wood"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_wood"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_planks"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_button"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_fence"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_fence_gate"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_pressure_plate"));

        pack.addLootTable(Motherlode.id(id, name -> "blocks/" + name + "_leaves"), table -> table
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
                        .name(Motherlode.id(id, name -> name + "_leaves"))
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
                        .name(Motherlode.id(id, name -> name + "_sapling"))
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

        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id, name -> name + "_sapling"));

        CommonData.BLOCK_TAG.apply(Motherlode.id(id, name -> id.getPath() + "_logs")).accept(pack, Motherlode.id(id, name -> name + "_log"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id, name -> id.getPath() + "_logs")).accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_log"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id, name -> id.getPath() + "_logs")).accept(pack, Motherlode.id(id, name -> name + "_wood"));
        CommonData.BLOCK_TAG.apply(Motherlode.id(id, name -> id.getPath() + "_logs")).accept(pack, Motherlode.id(id, name -> "stripped_" + name + "_wood"));
        CommonData.BLOCK_TAG_INCLUDE.apply(new Identifier("minecraft", "logs_that_burn")).accept(pack, Motherlode.id(id, name -> name + "_logs"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "planks")).accept(pack, Motherlode.id(id, name -> name + "_planks"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_buttons")).accept(pack, Motherlode.id(id, name -> name + "_button"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_fences")).accept(pack, Motherlode.id(id, name -> name + "_fence"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "fence_gates")).accept(pack, Motherlode.id(id, name -> name + "_fence_gate"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "pressure_plates")).accept(pack, Motherlode.id(id, name -> name + "_pressure_plate"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "leaves")).accept(pack, Motherlode.id(id, name -> name + "_leaves"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "saplings")).accept(pack, Motherlode.id(id, name -> name + "_sapling"));
    }

    public enum Variant {
        LOG,
        STRIPPED_LOG,
        WOOD,
        STRIPPED_WOOD,
        PLANKS,
        BUTTON,
        FENCE,
        FENCE_GATE,
        PRESSURE_PLATE,
        LEAVES,
        SAPLING,
        POTTED_SAPLING
    }
}
