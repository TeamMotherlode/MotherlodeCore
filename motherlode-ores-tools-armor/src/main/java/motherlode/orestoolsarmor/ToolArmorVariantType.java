package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.base.api.RegisterableVariantType;
import motherlode.orestoolsarmor.item.DefaultToolMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class ToolArmorVariantType implements RegisterableVariantType<Item>, ArtificeProcessor {

    public final Item PICKAXE;
    public final Item SWORD;
    public final Item AXE;
    public final Item SHOVEL;
    public final Item HOE;

    public final Item HELMET;
    public final Item CHESTPLATE;
    public final Item LEGGINGS;
    public final Item BOOTS;

    private final Map<Item, Identifier> ALL = new HashMap<>();

    public ToolArmorVariantType(Identifier id, DefaultToolMaterial toolMaterial, ArmorMaterial armorMaterial) {

        this.PICKAXE = new MaterialPickaxe(toolMaterial);
        this.SWORD = new SwordItem(toolMaterial, 3, -2.4F, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
        this.AXE = new MaterialAxe(toolMaterial);
        this.SHOVEL = new ShovelItem(toolMaterial, 1.5F, 3.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
        this.HOE = new MaterialHoe(toolMaterial);

        this.HELMET = new ArmorItem(armorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
        this.CHESTPLATE = new ArmorItem(armorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
        this.LEGGINGS = new ArmorItem(armorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
        this.BOOTS = new ArmorItem(armorMaterial, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

        ALL.put(this.PICKAXE, Motherlode.id(id.getNamespace(), id.getPath() + "_pickaxe"));
        ALL.put(this.SWORD, Motherlode.id(id.getNamespace(), id.getPath() + "_sword"));
        ALL.put(this.AXE, Motherlode.id(id.getNamespace(), id.getPath() + "_axe"));
        ALL.put(this.SHOVEL, Motherlode.id(id.getNamespace(), id.getPath() + "_shovel"));
        ALL.put(this.HOE, Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"));

        ALL.put(this.HELMET, Motherlode.id(id.getNamespace(), id.getPath() + "_helmet"));
        ALL.put(this.CHESTPLATE, Motherlode.id(id.getNamespace(), id.getPath() + "_chestplate"));
        ALL.put(this.LEGGINGS, Motherlode.id(id.getNamespace(), id.getPath() + "_leggings"));
        ALL.put(this.BOOTS, Motherlode.id(id.getNamespace(), id.getPath() + "_boots"));
    }

    @Override
    public void register(Identifier identifier) {

        for(Map.Entry<Item, Identifier> entry: ALL.entrySet()) {

            Registry.register(Registry.ITEM, entry.getValue(), entry.getKey());
        }
    }

    @Override
    public Item[] variants() {
        return ALL.keySet().toArray(new Item[ALL.size()]);
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

        CommonArtificeProcessors.HANDHELD_ITEM_MODEL.accept(pack, ALL.get(PICKAXE));
        CommonArtificeProcessors.HANDHELD_ITEM_MODEL.accept(pack, ALL.get(SWORD));
        CommonArtificeProcessors.HANDHELD_ITEM_MODEL.accept(pack, ALL.get(AXE));
        CommonArtificeProcessors.HANDHELD_ITEM_MODEL.accept(pack, ALL.get(SHOVEL));
        CommonArtificeProcessors.HANDHELD_ITEM_MODEL.accept(pack, ALL.get(HOE));

        CommonArtificeProcessors.DEFAULT_ITEM_MODEL.accept(pack, ALL.get(HELMET));
        CommonArtificeProcessors.DEFAULT_ITEM_MODEL.accept(pack, ALL.get(CHESTPLATE));
        CommonArtificeProcessors.DEFAULT_ITEM_MODEL.accept(pack, ALL.get(LEGGINGS));
        CommonArtificeProcessors.DEFAULT_ITEM_MODEL.accept(pack, ALL.get(BOOTS));
    }
}

class MaterialPickaxe extends PickaxeItem {
    public MaterialPickaxe(DefaultToolMaterial material) {
        super(material, 1, -2.8F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}

class MaterialAxe extends AxeItem {
    public MaterialAxe(DefaultToolMaterial material) {
        super(material, 6, -3.1F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}

class MaterialHoe extends HoeItem {
    public MaterialHoe(DefaultToolMaterial material) {
        super(material, -2, -1F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}
