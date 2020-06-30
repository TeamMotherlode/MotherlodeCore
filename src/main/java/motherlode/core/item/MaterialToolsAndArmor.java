package motherlode.core.item;

import motherlode.core.Motherlode;
import motherlode.core.registry.MotherlodeItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

public class MaterialToolsAndArmor {

    public final Item axe;
    public final Item hoe;
    public final Item pickaxe;
    public final Item shovel;
    public final Item sword;

    public final Item helmet;
    public final Item chestplate;
    public final Item leggings;
    public final Item boots;

    public MaterialToolsAndArmor(DefaultToolMaterial toolMaterial, ArmorMaterial armorMaterial) {
        this.axe = register(toolMaterial.getName() + "_axe", new MaterialAxe(toolMaterial));
        this.hoe = register(toolMaterial.getName() + "_hoe", new MaterialHoe(toolMaterial));
        this.pickaxe = register(toolMaterial.getName() + "_pickaxe", new MaterialPickaxe(toolMaterial));
        this.shovel = register(toolMaterial.getName() + "_shovel", new ShovelItem(toolMaterial, 1.5F, 3.0F, new Item.Settings().maxCount(1).group(Motherlode.ARMOUR_AND_TOOLS)));
        this.sword = register(toolMaterial.getName() + "_sword", new SwordItem(toolMaterial, 3, -2.4F, new Item.Settings().maxCount(1).group(Motherlode.ARMOUR_AND_TOOLS)));

        this.helmet = register(toolMaterial.getName() + "_helmet", new ArmorItem(armorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(Motherlode.ARMOUR_AND_TOOLS)));
        this.chestplate = register(toolMaterial.getName() + "_chestplate", new ArmorItem(armorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(Motherlode.ARMOUR_AND_TOOLS)));
        this.leggings = register(toolMaterial.getName() + "_leggings", new ArmorItem(armorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(Motherlode.ARMOUR_AND_TOOLS)));
        this.boots = register(toolMaterial.getName() + "_boots", new ArmorItem(armorMaterial, EquipmentSlot.FEET, new Item.Settings().group(Motherlode.ARMOUR_AND_TOOLS)));

    }

    private Item register(String id, Item item) {
        Registry.register(Registry.ITEM, Motherlode.id(id), item);
        MotherlodeItems.defaultItemModelList.add(item);
        return item;
    }

}

class MaterialPickaxe extends PickaxeItem {
    public MaterialPickaxe(DefaultToolMaterial material) {
        super(material, 1, -2.8F, new Item.Settings().maxCount(1).group(Motherlode.ARMOUR_AND_TOOLS));
    }
}

class MaterialAxe extends AxeItem {
    public MaterialAxe(DefaultToolMaterial material) {
        super(material, 6, -3.1F, new Item.Settings().maxCount(1).group(Motherlode.ARMOUR_AND_TOOLS));
    }
}

class MaterialHoe extends HoeItem {
    public MaterialHoe(DefaultToolMaterial material) {
        super(material, -2, -1F, new Item.Settings().maxCount(1).group(Motherlode.ARMOUR_AND_TOOLS));
    }
}
