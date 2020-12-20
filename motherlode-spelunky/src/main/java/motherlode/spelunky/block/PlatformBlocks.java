package motherlode.spelunky.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.spelunky.MotherlodeModule;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

public class PlatformBlocks extends MotherlodeVariantType<Block, PlatformBlocks> {
    private static final AbstractBlock.Settings DEFAULT_BLOCK_SETTINGS = FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(1.0f, 1.0f).nonOpaque();

    private final PlatformBlock platform;
    private final PlatformStairsBlock stairs;

    private final Item.Settings itemSettings;
    private final Identifier topTexture;

    public PlatformBlocks(Identifier id, Item.Settings itemSettings) {
        this(id, DEFAULT_BLOCK_SETTINGS, itemSettings);
    }

    public PlatformBlocks(Identifier id, Item.Settings itemSettings, Identifier topTexture) {
        this(id, DEFAULT_BLOCK_SETTINGS, itemSettings, topTexture);
    }

    public PlatformBlocks(Identifier id, MapColor color, Item.Settings itemSettings) {
        this(id, FabricBlockSettings.copyOf(DEFAULT_BLOCK_SETTINGS).mapColor(color), itemSettings);
    }

    public PlatformBlocks(Identifier id, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        this(id, settings, itemSettings, null);
    }

    public PlatformBlocks(Identifier id, AbstractBlock.Settings settings, Item.Settings itemSettings, Identifier topTexture) {
        super(id);

        this.platform = new PlatformBlock(settings);
        this.stairs = new PlatformStairsBlock(this.platform.getDefaultState(), settings);

        this.itemSettings = itemSettings;
        this.topTexture = topTexture;
    }

    public PlatformBlock getPlatform() {
        return this.platform;
    }

    public PlatformStairsBlock getStairs() {
        return this.stairs;
    }

    @Override
    protected PlatformBlocks getThis() {
        return this;
    }

    @Override
    protected Block[] baseVariants() {
        return new Block[] { this.platform, this.stairs };
    }

    @Override
    protected void registerBase(Identifier id) {
        Motherlode.register(
            Registerable.block(this.platform, this.itemSettings),
            Motherlode.id(id.getNamespace(), id.getPath() + "_platform"),
            this.platform
        );

        Motherlode.register(
            Registerable.block(this.stairs, this.itemSettings),
            Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs"),
            this.stairs
        );
    }

    @Override
    protected void registerOnClient(Identifier id) {
        BlockRenderLayerMap.INSTANCE.putBlock(this.platform, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(this.stairs, RenderLayer.getCutout());
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        // Platform
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_platform"), state -> state
            .variant("type=top", variant -> variant
                .uvlock(true)
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top")))
            .variant("type=bottom", variant -> variant
                .uvlock(true)
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_bottom")))
            .variant("type=double", variant -> variant
                .uvlock(true)
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_double"))
            )
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_top"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_top"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top")));

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_bottom"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_bottom"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top")));

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_double"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_double"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top")));

        CommonAssets.BLOCK_ITEM_FUNCTION.apply(Motherlode.id(id.getNamespace(), id.getPath() + "_platform")).accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_platform_top"));

        // Platform stairs
        pack.addBlockState(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs"), state -> state
            .variant("facing=east,shape=inner_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .uvlock(true)
                .rotationY(270))
            .variant("facing=east,shape=inner_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner")))
            .variant("facing=east,shape=outer_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .uvlock(true)
                .rotationY(270))
            .variant("facing=east,shape=outer_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer")))
            .variant("facing=east,shape=straight", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs")))
            .variant("facing=north,shape=inner_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .uvlock(true)
                .rotationY(180))
            .variant("facing=north,shape=inner_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .rotationY(270)
                .uvlock(true))
            .variant("facing=north,shape=outer_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .uvlock(true)
                .rotationY(180))
            .variant("facing=north,shape=outer_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .uvlock(true)
                .rotationY(270))
            .variant("facing=north,shape=straight", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs"))
                .rotationY(270)
                .uvlock(true))

            .variant("facing=south,shape=inner_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner")))
            .variant("facing=south,shape=inner_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .rotationY(90)
                .uvlock(true))
            .variant("facing=south,shape=outer_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer")))
            .variant("facing=south,shape=outer_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .uvlock(true)
                .rotationY(90))
            .variant("facing=south,shape=straight", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs"))
                .rotationY(90)
                .uvlock(true))
            .variant("facing=west,shape=inner_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .rotationY(90)
                .uvlock(true))
            .variant("facing=west,shape=inner_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_inner"))
                .rotationY(180)
                .uvlock(true))
            .variant("facing=west,shape=outer_left", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .rotationY(90)
                .uvlock(true))
            .variant("facing=west,shape=outer_right", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs_outer"))
                .uvlock(true)
                .rotationY(180))
            .variant("facing=west,shape=straight", variant -> variant
                .model(Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_stairs"))
                .rotationY(180)
                .uvlock(true)
            )
        );

        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_stairs"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top"))
        );
        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs_inner"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_stairs_inner"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top"))
        );
        pack.addBlockModel(Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs_outer"), model -> model
            .parent(MotherlodeModule.id("block/templates/platform_stairs_outer"))
            .texture("side", Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform"))
            .texture("top", this.topTexture != null ? this.topTexture : Motherlode.id(id.getNamespace(), "block/" + id.getPath() + "_platform_top"))
        );

        CommonAssets.BLOCK_ITEM.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs"));
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        // Platform
        pack.addLootTable(Motherlode.id(id.getNamespace(), id.getPath() + "_platform"), table -> table
            .type(new Identifier("minecraft", "block"))
            .pool(pool -> pool
                .rolls(1)
                .entry(entry -> entry
                    .type(new Identifier("minecraft", "item"))
                    .function(new Identifier("minecraft", "set_count"), function -> function
                        .condition(new Identifier("minecraft", "block_state_property"),
                            condition -> condition
                                .add("block", id.getNamespace() + ":" + id.getPath() + "_platform")
                                .addObject("properties", prop -> prop
                                    .add("type", "double"))
                        )
                        .jsonNumber("count", 2))
                    .function(new Identifier("minecraft:explosion_decay"), TypedJsonBuilder::build)
                    .name(Motherlode.id(id.getNamespace(), id.getPath() + "_platform"))
                )
            )
        );

        // Platform stairs
        CommonData.DEFAULT_BLOCK_LOOT_TABLE.accept(pack, Motherlode.id(id.getNamespace(), id.getPath() + "_platform_stairs"));
    }
}
