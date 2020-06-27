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

public class DefaultOreBlock extends DefaultBlock {
    public final boolean dropsExperience;
    public final int minExperience;
    public final int maxExperience;

    public DefaultOreBlock(Settings settings) {
        this(false, false, 0, 0, settings);
    }
    
    public DefaultOreBlock(boolean hasDefaultLootTable, Settings settings) {
        this(hasDefaultLootTable, false, 0, 0, settings);
    }

    public DefaultOreBlock(boolean dropsExperience, int minExperience, int maxExperience, Settings settings) {
        this(false, dropsExperience, minExperience, maxExperience, settings);
    }
    
    public DefaultOreBlock(boolean hasDefaultLootTable, boolean dropsExperience, int minExperience, int maxExperience, Settings settings) {
        super(true, true, true, hasDefaultLootTable, settings);

        this.dropsExperience = dropsExperience;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
    }


    protected int getExperienceWhenMined(Random random) {
        if (dropsExperience) {
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
}
