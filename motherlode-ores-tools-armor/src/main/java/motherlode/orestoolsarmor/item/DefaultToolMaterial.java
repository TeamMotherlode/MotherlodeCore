package motherlode.orestoolsarmor.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DefaultToolMaterial implements ToolMaterial {
    private String name;
    private final int durability, miningLevel, enchantability;
    private final float miningSpeed, attackDamage;
    private final Ingredient repairIngredient;

    public DefaultToolMaterial(String name, int durability, int miningLevel, int enchantability, float miningSpeed, float attackDamage, Ingredient repairIngredient) {
        this.durability = durability;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
}
