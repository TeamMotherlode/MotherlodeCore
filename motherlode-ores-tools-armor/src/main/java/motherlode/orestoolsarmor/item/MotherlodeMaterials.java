package motherlode.orestoolsarmor.item;

import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import motherlode.orestoolsarmor.MotherlodeOresToolsArmorItems;

public class MotherlodeMaterials {
    public static final DefaultToolMaterial COPPER_TOOLS =
        new DefaultToolMaterial("copper", 200, 1, 7, 5, 1.5F, Ingredient.ofItems(MotherlodeOresToolsArmorItems.COPPER_INGOT));
    public static final DefaultToolMaterial SILVER_TOOLS =
        new DefaultToolMaterial("silver", 400, 2, 16, 9, 2.5F, Ingredient.ofItems(MotherlodeOresToolsArmorItems.SILVER_INGOT));
    public static final DefaultToolMaterial CHARITE_TOOLS =
        new DefaultToolMaterial("charite", 3000, 5, 12, 10, 5, Ingredient.ofItems(MotherlodeOresToolsArmorItems.CHARITE_INGOT));
    public static final DefaultToolMaterial ECHERITE_TOOLS =
        new DefaultToolMaterial("echerite", 5000, 6, 16, 11, 6, Ingredient.ofItems(MotherlodeOresToolsArmorItems.ECHERITE_INGOT));
    public static final DefaultToolMaterial TITANIUM_TOOLS =
        new DefaultToolMaterial("titanium", 7500, 7, 10, 12, 7, Ingredient.ofItems(MotherlodeOresToolsArmorItems.TITANIUM_INGOT));
    public static final DefaultToolMaterial ADAMANTITE_TOOLS =
        new DefaultToolMaterial("adamantite", 10000, 8, 15, 13, 8F, Ingredient.ofItems(MotherlodeOresToolsArmorItems.SILVER_INGOT));

    public static final DefaultArmorMaterial COPPER_ARMOR =
        new DefaultArmorMaterial("copper", 10, new int[] {2, 4, 5, 2}, 10, 0, 0,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.COPPER_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());

    public static final DefaultArmorMaterial SILVER_ARMOR =
        new DefaultArmorMaterial("silver", 18, new int[] {3, 5, 7, 3}, 16, 0, 0,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.SILVER_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());

    public static final DefaultArmorMaterial CHARITE_ARMOR =
        new DefaultArmorMaterial("charite", 40, new int[] {4, 7, 9, 4}, 12, 4, 0.3F,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.CHARITE_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());

    public static final DefaultArmorMaterial ECHERITE_ARMOR =
        new DefaultArmorMaterial("echerite", 45, new int[] {5, 8, 10, 5}, 16, 5, 0.5F,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.ECHERITE_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());

    public static final DefaultArmorMaterial TITANIUM_ARMOR =
        new DefaultArmorMaterial("titanium", 50, new int[] {6, 9, 11, 6}, 10, 6, 0.75F,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.TITANIUM_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());

    public static final DefaultArmorMaterial ADAMANTITE_ARMOR =
        new DefaultArmorMaterial("adamantite", 55, new int[] {7, 10, 12, 7}, 15, 7, 1,
            Ingredient.ofItems(MotherlodeOresToolsArmorItems.ADAMANTITE_INGOT),
            ArmorMaterials.DIAMOND.getEquipSound());
}
