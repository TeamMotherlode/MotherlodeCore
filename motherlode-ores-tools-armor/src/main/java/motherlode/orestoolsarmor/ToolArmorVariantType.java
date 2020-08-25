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
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private final List<Pair<Identifier, Item>> ALL = new ArrayList<>();

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

        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_pickaxe"), this.PICKAXE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_sword"), this.SWORD));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_axe"), this.AXE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_shovel"), this.SHOVEL));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"), this.HOE));

        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_helmet"), this.HELMET));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_chestplate"), this.CHESTPLATE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_leggings"), this.LEGGINGS));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_boots"), this.BOOTS));
    }

    @Override
    public void register(Identifier identifier) {

        for(Pair<Identifier, Item> entry: ALL) {

            Registry.register(Registry.ITEM, entry.getLeft(), entry.getRight());
        }
    }

    @Override
    public Item[] variants() {
        return ALL.stream().map(Pair::getRight).collect(Collectors.toList()).toArray(new Item[ALL.size()]);
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

        for(Pair<Identifier, Item> entry: ALL) {

            CommonArtificeProcessors.DEFAULT_ITEM_MODEL.accept(pack, entry.getLeft());
        }
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
