package motherlode.base.api.woodtype;

import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.CommonAssets;
import motherlode.base.api.CommonData;
import motherlode.base.api.MotherlodeVariantType;
import motherlode.base.api.Registerable;
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
    private LeavesBlock leaves;
    private SaplingBlock sapling;

    private final MapColor logTopMapColor;
    private final MapColor logSideMapColor;
    private final BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator;
    private final Item.Settings itemSettings;

    public WoodType(Identifier id, MapColor logTopMapColor, MapColor logSideMapColor, BiFunction<BlockState, BlockState, SaplingGenerator> saplingGenerator, Item.Settings itemSettings) {
        super(id);

        this.logTopMapColor = logTopMapColor;
        this.logSideMapColor = logSideMapColor;
        this.saplingGenerator = saplingGenerator;
        this.itemSettings = itemSettings;
    }

    @Override
    protected WoodType getThis() {
        return this;
    }

    @Override
    protected void registerBase(Identifier id) {
        this.log = BlocksAccessor.callCreateLogBlock(logTopMapColor, logSideMapColor);
        this.strippedLog = BlocksAccessor.callCreateLogBlock(logTopMapColor, logTopMapColor);
        this.wood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.strippedWood = new PillarBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        this.planks = new Block(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.button = new DefaultWoodenButtonBlock(FabricBlockSettings.of(Material.SUPPORTED).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        this.fence = new FenceBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.fenceGate = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, logTopMapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        this.leaves = BlocksAccessor.callCreateLeavesBlock();
        this.sapling = new DefaultSaplingBlock(saplingGenerator.apply(this.log.getDefaultState(), this.leaves.getDefaultState()), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));

        Registerable.block(this.log, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_log"));
        Registerable.block(this.strippedLog, this.itemSettings).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_log"));
        Registerable.block(this.wood, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_wood"));
        Registerable.block(this.strippedWood, this.itemSettings).register(Motherlode.id(id.getNamespace(), "stripped_" + id.getPath() + "_wood"));
        Registerable.block(this.planks, this.itemSettings).register(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"));
        Registerable.block(this.button, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        Registerable.block(this.fence, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        Registerable.block(this.fenceGate, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));
        Registerable.block(this.leaves, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
        Registerable.block(this.sapling, this.itemSettings).register(Motherlode.id(id.getNamespace(), id.getPath() + "_sapling"));

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

    public LeavesBlock getLeaves() {
        return this.leaves;
    }

    public SaplingBlock getSapling() {
        return this.sapling;
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
        CommonAssets.DEFAULT_BLOCK.accept(pack, Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"));

        // Button
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_button"), state -> state
            .variant("face=ceiling,facing=east,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationY(270)
                .rotationX(180)
            )
            .variant("face=ceiling,facing=east,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationY(270)
                .rotationX(180)
            )
            .variant("face=ceiling,facing=north,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationY(180)
                .rotationX(180)
            )
            .variant("face=ceiling,facing=north,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationY(180)
                .rotationX(180)
            )
            .variant("face=ceiling,facing=south,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(180)
            )
            .variant("face=ceiling,facing=south,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(180)
            )
            .variant("face=ceiling,facing=west,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(180)
                .rotationY(90)
            )
            .variant("face=ceiling,facing=west,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(180)
                .rotationY(90)
            )
            .variant("face=floor,facing=east,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationY(90)
            )
            .variant("face=floor,facing=east,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationY(90)
            )
            .variant("face=floor,facing=north,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
            )
            .variant("face=floor,facing=north,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
            )
            .variant("face=floor,facing=south,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationY(180)
            )
            .variant("face=floor,facing=south,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationY(180)
            )
            .variant("face=floor,facing=west,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationY(270)
            )
            .variant("face=floor,facing=west,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationY(270)
            )
            .variant("face=wall,facing=east,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(90)
                .rotationY(90)
                .uvlock(true)
            )
            .variant("face=wall,facing=east,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(90)
                .rotationY(90)
                .uvlock(true)
            )
            .variant("face=wall,facing=north,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(90)
                .uvlock(true)
            )
            .variant("face=wall,facing=north,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(90)
                .uvlock(true)
            )
            .variant("face=wall,facing=south,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(90)
                .rotationY(180)
                .uvlock(true)
            )
            .variant("face=wall,facing=south,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(90)
                .rotationY(180)
                .uvlock(true)
            )
            .variant("face=wall,facing=west,powered=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button"))
                .rotationX(90)
                .rotationY(270)
                .uvlock(true)
            )
            .variant("face=wall,facing=west,powered=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_button_pressed"))
                .rotationX(90)
                .rotationY(270)
                .uvlock(true)
            )
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_button"), model -> model
            .parent(new Identifier("minecraft", "block/button"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_button_inventory"), model -> model
            .parent(new Identifier("minecraft", "block/button_inventory"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_button_pressed"), model -> model
            .parent(new Identifier("minecraft", "block/button_pressed"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        CommonAssets.BLOCK_ITEM_FUNCTION.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_button"))
            .accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_button_inventory"));

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
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"), state -> state
            .variant("facing=east,in_wall=false,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate"))
                .rotationY(270)
                .uvlock(true)
            )
            .variant("facing=east,in_wall=false,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_open"))
                .rotationY(270)
                .uvlock(true)
            )
            .variant("facing=east,in_wall=true,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall"))
                .rotationY(270)
                .uvlock(true)
            )
            .variant("facing=east,in_wall=true,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall_open"))
                .rotationY(270)
                .uvlock(true)
            )

            .variant("facing=north,in_wall=false,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate"))
                .rotationY(180)
                .uvlock(true)
            )
            .variant("facing=north,in_wall=false,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_open"))
                .rotationY(180)
                .uvlock(true)
            )
            .variant("facing=north,in_wall=true,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall"))
                .rotationY(180)
                .uvlock(true)
            )
            .variant("facing=north,in_wall=true,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall_open"))
                .rotationY(180)
                .uvlock(true)
            )

            .variant("facing=south,in_wall=false,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate"))
                .uvlock(true)
            )
            .variant("facing=south,in_wall=false,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_open"))
                .uvlock(true)
            )
            .variant("facing=south,in_wall=true,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall"))
                .uvlock(true)
            )
            .variant("facing=south,in_wall=true,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall_open"))
                .uvlock(true)
            )

            .variant("facing=west,in_wall=false,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate"))
                .rotationY(90)
                .uvlock(true)
            )
            .variant("facing=west,in_wall=false,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_open"))
                .rotationY(90)
                .uvlock(true)
            )
            .variant("facing=west,in_wall=true,open=false", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall"))
                .rotationY(90)
                .uvlock(true)
            )
            .variant("facing=west,in_wall=true,open=true", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_fence_gate_wall_open"))
                .rotationY(90)
                .uvlock(true)
            )
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"), model -> model
            .parent(new Identifier("minecraft", "block/template_fence_gate"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate_open"), model -> model
            .parent(new Identifier("minecraft", "block/template_fence_gate_open"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate_wall"), model -> model
            .parent(new Identifier("minecraft", "block/template_fence_gate_wall"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate_wall_open"), model -> model
            .parent(new Identifier("minecraft", "block/template_fence_gate_wall_open"))
            .texture("texture", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));

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
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_planks"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));

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
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_buttons")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_button"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "wooden_fences")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "fence_gates")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_fence_gate"));
        CommonData.BLOCK_TAG.apply(new Identifier("minecraft", "leaves")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_leaves"));
    }
}
