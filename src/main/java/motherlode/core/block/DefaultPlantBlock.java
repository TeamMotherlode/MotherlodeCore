package motherlode.core.block;

import motherlode.core.api.ArtificeProperties;
import motherlode.core.registry.MotherlodeBlocks;
import motherlode.core.registry.MotherlodeItems;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultPlantBlock extends PlantBlock implements ArtificeProperties {
    private final int height;

    public DefaultPlantBlock(int height, boolean useDefaultState, boolean useDefaultModel, Function<Block, Supplier<String>> textureName, Settings settings) {
        super(settings);
        this.height = height;
        if(useDefaultState) MotherlodeBlocks.defaultStateList.add(this);
        if(useDefaultModel) MotherlodeBlocks.defaultPlantModelList.add(this);
        MotherlodeBlocks.cutouts.add(this);
        MotherlodeBlocks.grassColored.add(this);
        MotherlodeBlocks.flatItemModelList.put(this, textureName.apply(this));
    }

    public DefaultPlantBlock(int height, boolean useDefaultState, boolean useDefaultModel, Settings settings) {
        this(height, useDefaultState, useDefaultModel, block -> () -> block.getTranslationKey().replace("block.motherlode.",""), settings);
    }

    public DefaultPlantBlock(int height, boolean useDefaultState, boolean useDefaultModel, String textureName, Settings settings) {
        this(height, useDefaultState, useDefaultModel, block -> () -> textureName, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isHoldingShovelOrSword(context) ? VoxelShapes.empty() : Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    @Override
    public boolean hasDefaultState() {
        return true;
    }

    @Override
    public boolean hasDefaultModel() {
        return false;
    }

    @Override
    public boolean hasDefaultItemModel() {
        return true;
    }

    @Override
    public boolean hasDefaultLootTable() {
        return false;
    }

    @Override
    public Block getBlockInstance() {
        return this;
    }

    public static boolean isHoldingShovelOrSword(ShapeContext ctx) {
        boolean b = false;
        for(Item e : Registry.ITEM) {
            if(e instanceof ShovelItem || e instanceof SwordItem) {
                if(ctx.isHolding(e)) b = true;
            }
        }
        return b;
    }
}
