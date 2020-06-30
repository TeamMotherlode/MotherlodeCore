package motherlode.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import motherlode.core.api.OreProperties;

public class DefaultOreBlock extends DefaultBlock implements OreProperties{
    public final int minExperience;
    public final int maxExperience;
    public final int veinSize;
    public final int veinsPerChunk;
    public final int minY;
    public final int maxY;

    public DefaultOreBlock(Settings settings) {
        this(false, 0, 0, 8, 1, 0, 50, settings);
    }
    
    public DefaultOreBlock(boolean hasDefaultLootTable, Settings settings) {
        this(hasDefaultLootTable, 0, 0, 8, 1, 0, 50, settings);
    }

    public DefaultOreBlock( int minExperience, int maxExperience, Settings settings) {
        this(false, minExperience, maxExperience, 8, 1, 0, 50, settings);
    }
    
    public DefaultOreBlock(boolean hasDefaultLootTable, int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY,  Settings settings) {
        super(true, true, true, hasDefaultLootTable, settings);

        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;

    }


    protected int getExperienceWhenMined(Random random) {
        if (maxExperience!=0) {
            return MathHelper.nextInt(random, minExperience, maxExperience);
        }
        return 0;
    }

    @Override
    public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            int i = this.getExperienceWhenMined(world.random);
            if (i > 0) {
                this.dropExperience(world, pos, i);
            }
        }
    }

    @Override
    public Block getBlockInstance() {
        return this;
    }

    @Override
    public int veinSize() {
        return veinSize;
    }

    @Override
    public int veinsPerChunk() {
        return veinsPerChunk;
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int maxY() {
        return maxY;
    }
}
