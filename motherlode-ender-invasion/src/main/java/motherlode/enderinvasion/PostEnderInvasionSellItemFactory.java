package motherlode.enderinvasion;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import motherlode.enderinvasion.component.EnderInvasionComponent;

public class PostEnderInvasionSellItemFactory implements TradeOffers.Factory {
    private final ItemStack sell;
    private final int price;
    private final int count;
    private final int maxUses;
    private final int experience;
    private final float multiplier;

    public PostEnderInvasionSellItemFactory(Block block, int price, int count, int maxUses, int experience) {
        this(new ItemStack(block), price, count, maxUses, experience);
    }

    public PostEnderInvasionSellItemFactory(Item item, int price, int count, int experience) {
        this((new ItemStack(item)), price, count, 12, experience);
    }

    public PostEnderInvasionSellItemFactory(Item item, int price, int count, int maxUses, int experience) {
        this(new ItemStack(item), price, count, maxUses, experience);
    }

    public PostEnderInvasionSellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
        this(stack, price, count, maxUses, experience, 0.05F);
    }

    public PostEnderInvasionSellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
        this.sell = stack;
        this.price = price;
        this.count = count;
        this.maxUses = maxUses;
        this.experience = experience;
        this.multiplier = multiplier;
    }

    public TradeOffer create(Entity entity, Random random) {
        if (EnderInvasion.STATE.get(entity.getEntityWorld().getLevelProperties()).value()
            .ordinal() >= EnderInvasionComponent.State.ENDER_INVASION.ordinal())

            return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);

        else return null;
    }
}
